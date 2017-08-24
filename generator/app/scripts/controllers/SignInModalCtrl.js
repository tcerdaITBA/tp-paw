define(['productSeek'], function(productSeek) {

    'use strict';
    productSeek.controller('SignInModalCtrl', function($scope) {
		// TODO: no me lei un effective javascript, capaz esto no se hace asi
        $scope.loginForm = {};
		
		$scope.loginForm.username = {};
		$scope.loginForm.password = {};

		$scope.loginForm.username.emailPattern = /^(([^<>()\[\]\.,;:\s@\"]+(\.[^<>()\[\]\.,;:\s@\"]+)*)|(\".+\"))@(([^<>()[\]\.,;:\s@\"]+\.)+[^<>()[\]\.,;:\s@\"]{2,})$/i;
		
		$scope.loginForm.password.passwordPattern; // TODO
				
		$scope.loginSubmit = function() {
			console.log($scope.loginForm);
		};
    });

});