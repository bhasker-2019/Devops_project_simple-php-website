#!/usr/bin/env groovy
import hudson.model.*
import java.net.URL

// Maintained by : Bhasker S
// Groovy script to deploy an simple PHP website using Jenkinsfile pipeline
// 1. Download project files from remote Git repository into Master node
// 2. Using Ansible playbook install Docker on slave machine 
// 3. Download the Simple-PHP-Website application code on slave node (Test Server)
// 4. Build and Deploy docker container using updated image with build number as tag
// 5. Perform the testing of the webpage using the selenium jar file
// 6. In case test failed delete the image and container from slave node




node ('master')
    {
        try
        {
            stage('Download configuration files (Playbook, groovy script) from git repo - Master server')
            {
                echo'===========Download config from Git repo============='            

               
                    git 'https://github.com/bhasker-2019/Devops_project_simple-php-website.git'
                } 

           stage('Run Ansible playbook from Master to install Docker on Test Server')
                {
                    echo 'Start installation of Docker on Test Server'
                    sh  'sudo ansible-playbook project_ansible_playbook.yml'
                    echo 'Completed Docker installation'
                }
        }
        catch(Exception err)
        {
                echo '****** Error in the Master ********'
                echo '${err}'
        }
        finally
        {
                echo 'Script execution in master is complete'
        }
    }
    
    
node ('slave')
    {
        try
        {
            stage ('Download PHP-Website files from git repository into Test Server')
            {
                echo'======Downloading PHP-Website files========'
                git 'https://github.com/bhasker-2019/Devops_project_simple-php-website.git'
            }
                
            stage('Build & Start container on Test Server')
            {
                echo '===============Build Image and Deploy================='
                echo '****checking if container exists and remove it ****'
                // Remove image and contaiver if exists 
                sh 'sudo docker rmi -f php-website || true'
                sh 'sudo docker stop container_php || true'
                sh 'sudo docker rm container_php || true' 
                // Build the latest image using Dockerfile
                sh 'sudo docker build . -t bhasker2019/php-website:${BUILD_NUMBER}'
                // Build and start the container
                sh 'sudo docker run -itd -p 80:80 --name container_php bhasker2019/php-website:${BUILD_NUMBER}'               
            }
                
           stage ('Test Webpage')
            {
                  echo '===========Starting the Test============'
                  testoutput = sh (script: 'java -jar MyProject_Testing.jar', , returnStdout:true).trim()
                  if(testoutput=="PASS")
                   {
                      echo 'Test has Passed!!!'
                    // If test is successful push the image to remote hub docker registry
                    sh 'sudo docker push bhasker2019/php-website:${BUILD_NUMBER}'
                   }
                else
                {
                    echo 'Test has Failed'
                    sh 'sudo docker stop container_php || true'
                    sh 'sudo docker rm container_php || true'
                    echo '------* Deleted the Container *--------'
                    sh 'sudo docker rmi -f bhasker2019/php-website:${BUILD_NUMBER} || true'
                    echo '------* Deleted the Image *--------'
                }
       
                      
                    
            }
            
        }

        catch(Exception err)
        {
            echo '****** Error ********'
            echo "${err}"
            echo 'The code has errors. Deleting the container and images'
            sh 'sudo docker stop container_php || true'
            sh 'sudo docker rm container_php || true'
            sh 'sudo docker rmi -f bhasker2019/php-website:${BUILD_NUMBER} || true'
        }
            finally
        {
            echo 'Script execution in slave is complete'
        }
    }



