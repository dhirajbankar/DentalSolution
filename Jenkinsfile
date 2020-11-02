Pipeline {

	agent any

	environment {
		mavenHome = tool 'myMaven'
		PATH = '$mavenHome/bin:$PATH'
	}

	stage('Checkout') {
		steps {
			sh 'mvn --version'
			echo 'Build'
			echo 'BUILD_NUMBER - $env.BUILD_NUMBER'
		}
	}
	stage('Compile') {
		steps {
			echo "Compile"
			sh 'mvn clean compile'
		}
	}
	stage('Test') {
		echo "Test"
		sh 'mvn test'
	}
}
