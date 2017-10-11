define(['productSeek'], function(productSeek) {

    'use strict';
    productSeek.controller('SignUpModalCtrl', function($scope) {
		
		$scope.email = {};

		$scope.email.emailPattern = /^(([^<>()\[\]\.,;:\s@\"]+(\.[^<>()\[\]\.,;:\s@\"]+)*)|(\".+\"))@(([^<>()[\]\.,;:\s@\"]+\.)+[^<>()[\]\.,;:\s@\"]{2,})$/i;
		
		$scope.signUpSubmit = function() {

        };
    });

});