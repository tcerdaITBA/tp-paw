define(['productSeek', 'services/authService', 'services/sessionService', 'services/snackbarService','directives/validFile', 'directives/ngFileRead', 'services/restService'], function(productSeek) {

    'use strict';
    productSeek.controller('ChangePictureModalCtrl', ['authService', 'sessionService','restService','$uibModalInstance', '$scope', '$rootScope','snackbarService', function(auth, session, restService, $uibModalInstance, $scope, $rootScope, snackbarService) {
        
        $scope.profilePicture = {};

        $scope.cancel = function(){
            $scope.$dismiss();
        };
        
        $scope.deletePicture = function() {
            $scope.profilePicture.picture = null;
        }

        $scope.uploadPicture = function(){
            if ($scope.pictureForm.$valid) {
                restService.changeProfilePicture($scope.profilePicture)
              .then(function(data) {
                $rootScope.$broadcast('user:picture', $scope.profilePicture.picture);
                snackbarService.showSnackbar('pictureChanged'); 
                $uibModalInstance.close();
              });              
          }
          else {
              console.log("Invalid form");
          }
        }
    }]);

});