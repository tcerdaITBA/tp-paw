define(['productSeek', 'services/authService', 'services/sessionService', 'services/restService'], function(productSeek) {

    'use strict';
    productSeek.controller('ChangePasswordModalCtrl', ['authService', 'sessionService', 'restService', '$scope', function(auth,session,restService, $scope) {
        $scope.passwordForm = {};
        $scope.passwordForm.oldPassword = {};
        $scope.passwordForm.newPassword = {};
        $scope.passwordForm.confirmPassword = {};
        
        $scope.passwordSubmit = function() {
            console.log($scope.passwordForm);
            //Falta validación de newPassword = confirmPassword
            oldPass = $scope.passwordForm.oldPassword.text;
            newPass = $scope.passwordForm.newPassword.text;
            confirm = $scope.passwordForm.confirmPassword.text;
            if(newPass === confirm){
                restService.changePassword(oldPass,newPass);
            } else {
                //Validaciones en caso de que las contraseñas ingresadas sean distintas
            }
        };
        
        $scope.cancel = function(){
            $scope.$dismiss();
        };
    }]);

});