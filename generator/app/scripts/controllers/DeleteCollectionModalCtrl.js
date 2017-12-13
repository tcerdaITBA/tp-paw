define(['productSeek', 'services/restService', 'services/snackbarService'], function(productSeek) {
'use strict';
    productSeek.controller('DeleteCollectionModalCtrl', ['$scope', '$uibModalInstance', 'restService', 'snackbarService', 'favList', function($scope, $uibModalInstance, restService, snackbarService, favList) {
        
        $scope.collection = favList;

        $scope.delete = function() {
			$scope.deleting = true;
            restService.deleteCollection(favList.id)
            .then(function(){
				$scope.deleting = false;
                snackbarService.showSnackbar('collectionDeleted');
                $uibModalInstance.close(true);
            })
			.catch(function() {
				$scope.deleting = false;
				snackbarService.showNoConnection();
			})
			;
        };
        
        $scope.cancel = function() {
            $uibModalInstance.dismiss('cancel');            
        };
        
    }]);

});