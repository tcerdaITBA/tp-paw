define(['productSeek', 'directives/ngFileRead', 'services/restService', 'services/authService'], function(productSeek) {

    'use strict';
    productSeek.controller('SignUpModalCtrl', ['$scope', 'authService', 'restService', function($scope, authService, restService) {
		
        $scope.user = {};
        
		$scope.emailPattern = /^(([^<>()\[\]\.,;:\s@\"]+(\.[^<>()\[\]\.,;:\s@\"]+)*)|(\".+\"))@(([^<>()[\]\.,;:\s@\"]+\.)+[^<>()[\]\.,;:\s@\"]{2,})$/i;
		
		$scope.signUpSubmit = function() {
            if ($scope.signUpForm.$valid) {
  				console.log("Valid form");
				restService.createUser($scope.user)
                .then(function(data) {
                    authService.logIn($scope.user.email, $scope.user.password, true);
                });              
            }
            else {
                console.log("Invalid form");
            }
        };
        
        $scope.deletePicture = function() {
            $scope.user.picture = null;
        }
    }]);

});