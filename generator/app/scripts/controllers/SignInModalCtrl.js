define(['productSeek', 'services/authService'], function(productSeek) {

    'use strict';
    productSeek.controller('SignInModalCtrl', ['authService', '$scope', function(auth, $scope) {
		// TODO: no me lei un effective javascript, capaz esto no se hace asi
        $scope.loginForm = {};
		
		$scope.loginForm.username = {};
		$scope.loginForm.password = {};

		$scope.loginForm.username.emailPattern = /^(([^<>()\[\]\.,;:\s@\"]+(\.[^<>()\[\]\.,;:\s@\"]+)*)|(\".+\"))@(([^<>()[\]\.,;:\s@\"]+\.)+[^<>()[\]\.,;:\s@\"]{2,})$/i;
		
		$scope.loginForm.password.passwordPattern; // TODO
				
		$scope.loginSubmit = function() {
			console.log($scope.loginForm);
			auth.logIn($scope.loginForm.username.text, $scope.loginForm.password.text)
			.then(function(response) {
				console.log("LOGGED IN!");
				console.log(auth.getLoggedUser());
				console.log(auth.isLoggedIn());
			})
			.catch(function(response) {
				console.log("Invalid user");
				console.log(response);
			});
		};
    }]);

});