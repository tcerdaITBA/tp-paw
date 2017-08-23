define(['productSeek'], function(productSeek) {

    'use strict';
    productSeek.controller('SignInModalCtrl', function($scope) {
        $scope.loginForm = {};
		
		$scope.loginForm.username = {};

		$scope.loginForm.username.emailPattern = /^(([^<>()\[\]\.,;:\s@\"]+(\.[^<>()\[\]\.,;:\s@\"]+)*)|(\".+\"))@(([^<>()[\]\.,;:\s@\"]+\.)+[^<>()[\]\.,;:\s@\"]{2,})$/i;
		
		$scope.loginSubmit = function() {
			console.log($scope.loginForm);
		};
    });

});