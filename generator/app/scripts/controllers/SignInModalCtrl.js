define(['productSeek', 'services/authService', 'services/snackbarService'], function(productSeek) {

    'use strict';
    productSeek.controller('SignInModalCtrl', ['authService', '$scope', '$uibModalInstance', 'snackbarService', function(auth, $scope, $signInModal, snackbarService) {
        $scope.loginForm = {};
		
		$scope.loginForm.username = {};
		$scope.loginForm.password = {};

        $scope.invalidUser = false;
		$scope.loggingIn = false;
				
		$scope.loginSubmit = function() {
            if($scope.form.$valid) {
				$scope.loggingIn = true;
				
                auth.logIn($scope.loginForm.username.text, $scope.loginForm.password.text, $scope.loginForm.rememberMe)
                .then(function(response) {
                    $scope.invalidUser = false;
					$scope.loggingIn = false;
                    $signInModal.close(true);
                })
                .catch(function(error) {
					$scope.loggingIn = false;
					switch (error.status) {
						case -1: // Sin conexión
							snackbarService.showNoConnection();
							break;
						case 401: // Conflict - Nombre repetido
							$scope.invalidUser = true;
							break;
					}
                });
            }
		};
    }]);

});