define(['productSeek', 'directives/validFile', 'directives/ngFileRead', 'services/restService', 'services/authService'], function(productSeek) {

    'use strict';
    productSeek.controller('SignUpModalCtrl', ['$scope', '$uibModalInstance', 'authService', 'restService', function($scope, $uibModalInstance, authService, restService) {
		
        $scope.user = {};
        $scope.duplicateEmailError = false;
        
		$scope.emailPattern = /^(([^<>()\[\]\.,;:\s@\"]+(\.[^<>()\[\]\.,;:\s@\"]+)*)|(\".+\"))@(([^<>()[\]\.,;:\s@\"]+\.)+[^<>()[\]\.,;:\s@\"]{2,})$/i;
        
        var checkPasswordsMatch = function() {
            $scope.passwordsMatch = $scope.user.password === $scope.user.passwordConf;
        };
        
        $scope.$watch('user.password', checkPasswordsMatch);
        $scope.$watch('user.passwordConf', checkPasswordsMatch);
        $scope.$watch('user.email', function() {
            $scope.duplicateEmailError = false;
        });
		
		$scope.signUpSubmit = function() {
            checkPasswordsMatch();
            if ($scope.signUpForm.$valid) {
                $scope.duplicateEmailError = false;
  				$scope.loggingIn = true;

				restService.createUser($scope.user)
                .then(function(data) {
                    return authService.logIn($scope.user.email, $scope.user.password, true);
                })
                .then(function() {
					$scope.loggingIn = false;
                    $uibModalInstance.close(true);
                })
                .catch(function() {
                    $scope.duplicateEmailError = true;
                    $scope.signUpForm.email.$invalid = true;
                    $scope.loggingIn = false;
                });
            }
        };
        
        $scope.deletePicture = function() {
            $scope.user.picture = null;
        }
		
		
    }]);

});