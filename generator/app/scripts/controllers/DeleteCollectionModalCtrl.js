define(['productSeek', 'services/restService', 'services/snackbarService'], function(productSeek) {
'use strict';
    productSeek.controller('DeleteCollectionModalCtrl', ['$scope', '$uibModalInstance', 'restService', 'snackbarService', 'favList', function($scope, $uibModalInstance, restService, snackbarService, favList) {
        
        $scope.collection = favList;

        $scope.delete = function() {
            restService.deleteCollection(favList.id)
            .then(function(){
                snackbarService.showSnackbar('collectionDeleted');
                $uibModalInstance.close(true);
            });
        };
        
        $scope.cancel = function() {
            $uibModalInstance.dismiss('cancel');            
        };
        
    }]);

});