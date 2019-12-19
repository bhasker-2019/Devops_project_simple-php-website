# Simple PHP Website

I put together this project while learning DevOps to automate deployment of  an simple php website using Jenkins pipeline

Below are the steps followed:

1. Download project files from remote Git repository into Master node
2. Using Ansible playbook install Docker on slave machine 
3. Download the Simple-PHP-Website application code on slave node (Test Server)
4. Build and Start docker container using updated image
5. Perform the testing of the webpage using the selenium jar file
6. In case test failed delete the image and container from slave node

Modify readme file and adding simple text
