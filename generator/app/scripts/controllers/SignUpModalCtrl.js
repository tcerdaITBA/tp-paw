define(['productSeek'], function(productSeek) {

    'use strict';
    productSeek.controller('SignUpModalCtrl', function($scope) {
        $scope.signUpForm = {};
		
		$scope.signUpForm.username = {};

		$scope.signUpForm.username.emailPattern = /^(([^<>()\[\]\.,;:\s@\"]+(\.[^<>()\[\]\.,;:\s@\"]+)*)|(\".+\"))@(([^<>()[\]\.,;:\s@\"]+\.)+[^<>()[\]\.,;:\s@\"]{2,})$/i;
		
		$scope.signUpSubmit = function() {
			console.log($scope.signUpForm);
		};
    });

});