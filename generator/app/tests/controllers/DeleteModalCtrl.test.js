define(['controllers/DeleteModalCtrl', 'angular-mocks'], function() {
	describe('Delete Modal Controller', function() {
		var $controller, $uibModalInstance, restService, snackbarService, $q, $_rootScope_;
		var deleteModalCtrl, $scope = {};
		var DUMMY_PRODUCT = {id: '1', name: 'slime rancher', category: 'game'};
		var DUMMY_MODAL_INSTANCE = {
			close: function(value){},
			dismiss: function(value){}
		};

		beforeEach(module('productSeek'));

		beforeEach(inject(function(_$controller_, _restService_, _snackbarService_, _$q_, _$rootScope_) {
			$controller = _$controller_;
			restService = _restService_;
			$q = _$q_;
			$rootScope = _$rootScope_;
			snackbarService = _snackbarService_;

			spyOn(restService, 'deleteProduct').and.returnValue($q.when(DUMMY_PRODUCT));
			spyOn(DUMMY_MODAL_INSTANCE, 'close');
			spyOn(DUMMY_MODAL_INSTANCE, 'dismiss');
			spyOn(snackbarService, 'showSnackbar');

			deleteModalCtrl = $controller('DeleteModalCtrl', {$scope: $scope, $uibModalInstance: DUMMY_MODAL_INSTANCE, 
				restService: restService, snackbarService: snackbarService, product: DUMMY_PRODUCT});
		}));

		describe('$scope.product', function() {
			it('should be defined', function() {
				expect($scope.product).toBeDefined();
			});

			it('should be defined with the resolved "product"', function() {
				expect($scope.product).toEqual(DUMMY_PRODUCT);
			});
		});

		describe('$scope.delete()', function() {

			beforeEach(function() {
				$scope.delete();
				$rootScope.$apply(); // resolves promises
			});

			it('should be defined', function() {
				expect($scope.delete).toBeDefined();
			});

			it('should delete the product through the API', function() {
				expect(restService.deleteProduct).toHaveBeenCalledWith(DUMMY_PRODUCT.id);
			});

			it('should close the modal indicating the product has been deleted', function() {
				expect(snackbarService.showSnackbar).toHaveBeenCalledWith('productDeleted');
				expect(DUMMY_MODAL_INSTANCE.close).toHaveBeenCalledWith(true);
			});
		});

		describe('$scope.cancel()', function() {

			beforeEach(function() {
				$scope.cancel();
			});

			it('should be defined', function() {
				expect($scope.cancel).toBeDefined();
			});

			it('should dismiss the modal with the "cancel" message', function() {
				expect(DUMMY_MODAL_INSTANCE.dismiss).toHaveBeenCalledWith('cancel');
			});
		});
	});
});