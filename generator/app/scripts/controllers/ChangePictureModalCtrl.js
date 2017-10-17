define(['productSeek', 'services/authService', 'services/sessionService', 'directives/validFile', 'directives/ngFileRead', 'services/restService'], function(productSeek) {

    'use strict';
    productSeek.controller('ChangePictureModalCtrl', ['authService', 'sessionService','restService','$uibModalInstance', '$scope', '$rootScope', function(auth, session, restService, $uibModalInstance, $scope, $rootScope) {
        
        $scope.profilePicture = {};

        $scope.cancel = function(){
            $scope.$dismiss();
        };
        
        $scope.deletePicture = function() {
            $scope.profilePicture.picture = null;
        }

        $scope.uploadPicture = function(){
            if ($scope.pictureForm.$valid) {
                console.log("Valid form");
                restService.changeProfilePicture($scope.profilePicture)
              .then(function(data) {
                $rootScope.$broadcast('user:picture', $scope.profilePicture.picture);
                $uibModalInstance.close();
                //SnackBar
              });              
          }
          else {
              console.log("Invalid form");
          }
        }
    }]);

});