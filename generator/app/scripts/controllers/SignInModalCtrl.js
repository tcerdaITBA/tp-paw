define(['productSeek', 'services/authService'], function(productSeek) {

    'use strict';
    productSeek.controller('SignInModalCtrl', ['authService', '$scope', '$uibModalInstance', function(auth, $scope, $signInModal) {
        $scope.loginForm = {};
		
		$scope.loginForm.username = {};
		$scope.loginForm.password = {};

		$scope.loginForm.username.emailPattern = /^(([^<>()\[\]\.,;:\s@\"]+(\.[^<>()\[\]\.,;:\s@\"]+)*)|(\".+\"))@(([^<>()[\]\.,;:\s@\"]+\.)+[^<>()[\]\.,;:\s@\"]{2,})$/i;
		
		$scope.loginForm.password.passwordPattern; // TODO
				
		$scope.loginSubmit = function() {
			console.log($scope.loginForm);
			auth.logIn($scope.loginForm.username.text, $scope.loginForm.password.text, $scope.loginForm.rememberMe)
			.then(function(response) {
				alert('Logged In!')
				console.log(auth.getLoggedUser());
				$signInModal.dismiss();
			})
			.catch(function(response) {
				alert('Invalid user');
				console.log(response);
			});
		};
    }]);

});