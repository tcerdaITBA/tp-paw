define(['controllers/DeleteModalCtrl', 'angular-mocks'], function() {
	describe('Delete Modal Controller', function() {
		var $controller, $uibModalInstance, restService, $q, $_rootScope_;
		var deleteModalCtrl, $scope = {};
		var DUMMY_PRODUCT = {id: '1', name: 'slime rancher', category: 'game'};
		var DUMMY_MODAL_INSTANCE = {
			close: function(value){},
			dismiss: function(value){}
		};

		beforeEach(module('productSeek'));

		beforeEach(inject(function(_$controller_, _restService_, _$q_, _$rootScope_) {
			$controller = _$controller_;
			restService = _restService_;
			$q = _$q_;
			$rootScope = _$rootScope_;

			spyOn(restService, 'deleteProduct').and.returnValue($q.when(DUMMY_PRODUCT));
			spyOn(DUMMY_MODAL_INSTANCE, 'close');
			spyOn(DUMMY_MODAL_INSTANCE, 'dismiss');

			deleteModalCtrl = $controller('DeleteModalCtrl', {$scope: $scope, 
				$uibModalInstance: DUMMY_MODAL_INSTANCE, restService: restService, product: DUMMY_PRODUCT});
		}));

		describe('$scope.product', function() {
			it('should have $scope.product defined', function() {
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

			it('should call restService.delete() with the resolved product id', function() {
				expect(restService.deleteProduct).toHaveBeenCalledWith(DUMMY_PRODUCT.id);
			});

			it('should call $uibModalInstance.close() with true', function() {
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

			it('should call $uibModalInstance.dismiss() with "cancel"', function() {
				expect(DUMMY_MODAL_INSTANCE.dismiss).toHaveBeenCalledWith('cancel');
			});
		})
	});
});