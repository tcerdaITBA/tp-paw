define(['productSeek', 'services/authService'], function(productSeek) {

    'use strict';
    productSeek.controller('SignInModalCtrl', ['authService', '$scope', '$uibModalInstance', function(auth, $scope, $signInModal) {
        $scope.loginForm = {};
		
		$scope.loginForm.username = {};
		$scope.loginForm.password = {};

        $scope.invalidUser = false;
				
		$scope.loginSubmit = function() {
			console.log($scope.loginForm);
            if($scope.form.$valid) {
                auth.logIn($scope.loginForm.username.text, $scope.loginForm.password.text, $scope.loginForm.rememberMe)
                .then(function(response) {
                    $scope.invalidUser = false;
                    $signInModal.dismiss();
                })
                .catch(function(response) {
                    $scope.invalidUser = true;
                });
            }
		};
    }]);

});