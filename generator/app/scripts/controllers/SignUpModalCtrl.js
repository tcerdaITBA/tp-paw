define(['productSeek', 'directives/validFile', 'directives/ngFileRead', 'services/restService', 'services/authService'], function(productSeek) {

    'use strict';
    productSeek.controller('SignUpModalCtrl', ['$scope', '$uibModalInstance', 'authService', 'restService', function($scope, $uibModalInstance, authService, restService) {
		
        $scope.user = {};
        
		$scope.emailPattern = /^(([^<>()\[\]\.,;:\s@\"]+(\.[^<>()\[\]\.,;:\s@\"]+)*)|(\".+\"))@(([^<>()[\]\.,;:\s@\"]+\.)+[^<>()[\]\.,;:\s@\"]{2,})$/i;
        
        var checkPasswordsMatch = function() {
            $scope.passwordsMatch = $scope.user.password === $scope.user.passwordConf;
        };
        
        $scope.$watch('user.password', checkPasswordsMatch);
        $scope.$watch('user.passwordConf', checkPasswordsMatch);
		
		$scope.signUpSubmit = function() {
            checkPasswordsMatch();
            if ($scope.signUpForm.$valid) {
  				$scope.loggingIn = true;
				restService.createUser($scope.user)
                .then(function(data) {
                    return authService.logIn($scope.user.email, $scope.user.password, true);
                })
                .then(function() {
					$scope.loggingIn = false;
                    $uibModalInstance.close(true);
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