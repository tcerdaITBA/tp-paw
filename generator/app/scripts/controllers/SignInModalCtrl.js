define(['productSeek', 'services/authService'], function(productSeek) {

    'use strict';
    productSeek.controller('SignInModalCtrl', ['authService', '$scope', '$uibModalInstance', function(auth, $scope, $signInModal) {
        $scope.loginForm = {};
		
		$scope.loginForm.username = {};
		$scope.loginForm.password = {};

        $scope.invalidUser = false;
		$scope.loggingIn = false;
				
		$scope.loginSubmit = function() {
            if($scope.form.$valid) {
				$scope.loggingIn = true;
				
                auth.logIn($scope.loginForm.username.text, $scope.loginForm.password.text, $scope.loginForm.rememberMe)
                .then(function(response) {
                    $scope.invalidUser = false;
					$scope.loggingIn = false;
                    $signInModal.close(true);
                })
                .catch(function(response) {
					$scope.loggingIn = false;
                    $scope.invalidUser = true;
                });
            }
		};
    }]);

});