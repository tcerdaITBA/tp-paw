define(['productSeek', 'services/authService', 'services/sessionService'], function(productSeek) {

    'use strict';
    productSeek.controller('ChangePasswordModalCtrl', ['authService', 'sessionService', '$scope', function(auth,session, $scope) {
        $scope.passwordForm = {};
        $scope.passwordForm.oldPassword = {};
        $scope.passwordForm.newPassword = {};
        $scope.passwordForm.confirmPassword = {};
        
        $scope.passwordSubmit = function() {
            console.log($scope.passwordForm);
            var user = session.getUser();
            // user.password = $scope.passwordForm.newPassword.text;
			
        };
        
        $scope.cancel = function(){
            $scope.$dismiss();
        };
    }]);

});