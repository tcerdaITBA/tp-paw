define(['productSeek', 'services/authService', 'services/sessionService', 'services/restService'], function(productSeek) {

    'use strict';
    productSeek.controller('ChangePasswordModalCtrl', ['authService', 'sessionService', 'restService', '$scope', '$uibModalInstance', function(auth,session,restService, $scope, $modal) {
        $scope.password = {};

        //Falta el password Pattern

        $scope.cancel = function(){
            $scope.$dismiss();
        };

        var checkPasswordsMatch = function() {
            $scope.passwordsMatch = $scope.password.newPassword === $scope.password.confirmPassword;
        }

        $scope.$watch('password.newPassword', checkPasswordsMatch);
        $scope.$watch('password.confirmPassword', checkPasswordsMatch);
        
        $scope.passwordSubmit = function() {
            checkPasswordsMatch();
            if($scope.passwordForm.$valid) {
                restService.changePassword($scope.password.oldPassword,$scope.password.newPassword)
                .then(function(response) {
                    $scope.invalidPassword = false;                    
                    alert('Password Changed') 
                    //SnackBar                   
                    $modal.dismiss();
                })
                .catch(function(response) {
                    $scope.invalidPassword = true;
                });
            }
        }


    }]);

});