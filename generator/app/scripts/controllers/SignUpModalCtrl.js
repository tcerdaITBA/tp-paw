define(['productSeek', 'directives/ngFileRead', 'services/restService'], function(productSeek) {

    'use strict';
    productSeek.controller('SignUpModalCtrl', ['$scope', '$location', 'restService', function($scope, $location, restService) {
		
        $scope.user = {};
        $scope.email = {};
        $scope.password = {};
        
        
		$scope.email = {};

		$scope.email.pattern = /^(([^<>()\[\]\.,;:\s@\"]+(\.[^<>()\[\]\.,;:\s@\"]+)*)|(\".+\"))@(([^<>()[\]\.,;:\s@\"]+\.)+[^<>()[\]\.,;:\s@\"]{2,})$/i;
		
		$scope.signUpSubmit = function() {
            
        };
        
        $scope.deletePicture = function() {
            
        }
    }]);

});