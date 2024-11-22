pipeline {
    agent any

    environment {
        REPO_URL = "http://${env.MAVEN_URL}/repository/maven-snapshots/"
        REPO_ID = "snapshots"
        PROJECT_NAME = "${env.JOB_NAME}"
        DIR_API = "${env.DIR_KEY}-api"
        DIR_SERVICE = "${env.DIR_KEY}-service"
        IMAGE_NAME = "${env.REGISTRY_URL}/${PROJECT_NAME}:${env.BUILD_NUMBER}"
    }

    tools {
        maven 'M3'
    }

    stages {
        stage('构建并发布') {
            steps {
                script {
                    sh '''
                        echo '============================== 构建并发布 =============================='
                        mvn clean deploy -DaltDeploymentRepository=${REPO_ID}::default::${REPO_URL}
                    '''
                    dir(DIR_API) {
                        sh "mvn clean deploy -DaltDeploymentRepository=${REPO_ID}::default::${REPO_URL}"
                    }
                }
            }
        }
        stage('构建镜像') {
            steps {
                script {
                    sh '''
                        echo '============================== 构建镜像 =============================='
                        docker build -t ${IMAGE_NAME} -f ../Dockerfile ./${DIR_SERVICE}/
                    '''
                }
            }
        }
        stage('上传镜像') {
            steps {
                sh '''
                    echo '============================== 上传镜像 =============================='
                    docker push ${IMAGE_NAME}
                '''
            }
        }
        stage('运行镜像') {
            steps {
                sh '''
                    echo '============================== 运行镜像 =============================='
                    if [ -n \"\$(docker ps -q -f name=${PROJECT_NAME})" ]; then
                        docker stop ${PROJECT_NAME}
                    fi
                    if [ -n \"\$(docker ps -aq -f name=${PROJECT_NAME})" ]; then
                        docker rm ${PROJECT_NAME}
                    fi
                    docker pull ${IMAGE_NAME}
                    docker run -d --name ${PROJECT_NAME} ${IMAGE_NAME}
                    sleep 10
                    docker logs ${PROJECT_NAME}
                '''
            }
        }
    }
}