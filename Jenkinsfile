#!/usr/bin/env groovy

// Maintained by : Bhaser S
// Groovy script to deploy an simple PHP website using Jenkinsfile pipeline
// 1. Download project files from remote Git repository into Master node
// 2. Using Ansible playbook install Docker on slave machine 
// 3. Download the Simple-PHP-Website application code on slave node (Test Server)
// 4. Build and Deploy docker container using updated image with build number as tag
// 5. Perform the testing of the webpage  using the jar file
// 6. In case test failed delete the image and container from slave node
// 7. In case test passed deploy the changes on production server 

import java.net.URL

// globale variable to store test result
def testresult =''

node ('master')
    {
        try
        {
            stage('Download configuration files (Playbook, groovy script) from git repo - Master server')
            {
                echo'===========Download config from Git repo============='            
                    
                    sh 'whoami'
                    git branch: 'master', url: 'https://github.com/bhasker-2019/Devops_project_simple-php-website.git'
                    sh 'pwd'
                } 

           stage('Run Ansible playbook from Master to install Docker on Test Server')
                {
                    echo 'Start installation of Docker on Test Server'
                    sh 'sudo ansible-playbook project_ansible_playbook.yml'
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
    
    
node ('Slave2')
    {
        try
        {
            stage ('Download PHP-Website files from git repository into Test Server')
            {
                echo'======Downloading PHP-Website files========'
                git 'https://github.com/bhasker-2019/Devops_project_simple-php-website.git'
            }
                
            stage('Build & Deploy container on Test Server')
            {
                echo '===============Build Image and Deploy================='
                echo '****checking if container exists and remove it ****'
                // Check if container exists on remote server 
                sh 'sudo docker rm -f php-website || true'
                // Build the latest image using Dockerfile
                sh 'sudo docker build . -t bhasker2019/php-website:v${BUILD_NUMBER}'
                // Build and start the container
                sh 'sudo docker run -itd -p 80:80 --name container_php bhasker2019/php-website:v${BUILD_NUMBER}'               
            }
                
            stage ('Test Webpage')
            {
                echo '===========Starting the Test============'
                testoutput = sh (script: 'java -jar MyProject_Testing.jar', , returnStdout:true).trim()
                if(testoutput=="PASS")
                {
                    echo 'Test has Passed!!!'
                    // If test is successful push the image to docker registry
                    sh 'sudo docker push bhasker2019/php-website:v${BUILD_NUMBER}'
                    testresult="PASS"
                }
                else
                {
                    echo 'Test has Failed'
                    sh 'sudo docker stop container_php || true'
                    sh 'sudo docker rm container_php || true'
                    echo '------* Deleted the Container *--------'
                    sh 'sudo docker rmi -f bhasker2019/php-website:v${BUILD_NUMBER} || true'
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
            sh 'sudo docker rmi -f bhasker2019/php-website:v${BUILD_NUMBER} || true'
        }
            finally
        {
            echo 'Script execution in slave is complete'
        }
    }
//node('master')
//    {
//        stage('Production Deployment')
//        {
//            if(testresult=="PASS")
//            {
//                echo 'deploying the latest image in production'
                //execute the ansible playbook to deploy the code on production
                //sh 'ansible-playbook Deploy-prod-website.yml'                
//                sh 'sudo docker rm -f website-prod ||true'
//                sh 'sudo docker run -itd --name website-prod -p 80:80 smartbond/simple-php-website:v${BUILD_NUMBER}'
//            }
//            else
//            {
//                echo 'Since test failed, no update to production'
//            }
//        }


//    }
