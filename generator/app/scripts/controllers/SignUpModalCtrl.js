define(['productSeek', 'directives/validFile', 'directives/ngFileRead', 'services/restService', 'services/authService', 'services/snackbarService'], function(productSeek) {

    'use strict';
    productSeek.controller('SignUpModalCtrl', ['$scope', '$uibModalInstance', 'authService', 'restService', 'snackbarService', function($scope, $uibModalInstance, authService, restService, snackbarService) {
		
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
                .catch(function(error) {
                    $scope.loggingIn = false;
					switch (error.status) {
						case -1:
							snackbarService.showNoConnection();
							break;
						case 409:
							$scope.duplicateEmailError = true;
							$scope.signUpForm.email.$invalid = true;
							break;
					}
                });
            }
        };
        
        $scope.deletePicture = function() {
            $scope.user.picture = null;
        }
		
		
    }]);

});