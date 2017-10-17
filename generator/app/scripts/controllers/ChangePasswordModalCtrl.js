define(['productSeek', 'services/authService', 'services/sessionService', 'services/restService'], function(productSeek) {

    'use strict';
    productSeek.controller('ChangePasswordModalCtrl', ['authService', 'sessionService', 'restService', '$scope', '$uibModalInstance', function(auth,session,restService, $scope, $modal) {
        $scope.passwordForm = {};
        $scope.passwordForm.oldPassword = {};
        $scope.passwordForm.newPassword = {};
        $scope.passwordForm.confirmPassword = {};
        
        $scope.cancel = function(){
            $scope.$dismiss();
        };

        $scope.passwordSubmit = function() {
            console.log($scope.passwordForm);
            
            if($scope.passwordForm.newPassword.text === $scope.passwordForm.confirmPassword.text) {
                console.log("Son iguales");
                restService.changePassword($scope.passwordForm.oldPassword.text,$scope.passwordForm.newPassword.text)
                .then(function(response) {
                    alert('Password Changed')
                    $modal.dismiss();
                })
                .catch(function(response) {
                    alert('La contraseña actual no es la correcta');
                    console.log(response);
                });
            } else {
                    console.log("Son distintas");
                };	
		};

    }]);

});