define(['controllers/CollectionModalCtrl', 'angular-mocks'], function() {
	describe('Collection Modal Controller', function() {
		var $controller, snackbarService, restService, $q, $_rootScope_;
		var collectionModalCtrl, $scope = {};
		var DUMMY_PRODUCT = {id: '1', name: 'slime rancher', category: 'game'};
		var DUMMY_COLLECTION = {id: '2', name: 'slime collection', products: [
			{id: '3', name: 'slime watch', category: 'gadget'},
		  	{id: '4', name: 'slime jingle', category: 'music'}
		]};
		var DUMMY_EXISTING_PRODUCT_COLLECTION = {id: '6', name: 'true slime collection', products: [
			DUMMY_PRODUCT,
			{id: '3', name: 'slime watch', category: 'gadget'},
		  	{id: '4', name: 'slime jingle', category: 'music'}
		]};
		var DUMMY_NEW_COLLECTION = {id: '5', name: 'new slime collection', products: []};
		var DUMMY_COLLECTION_OBJECT = {collections: [DUMMY_COLLECTION, DUMMY_EXISTING_PRODUCT_COLLECTION]};
		var DUMMY_MODAL_INSTANCE = {
			close: function(value){},
			dismiss: function(value){}
		};

		beforeEach(module('productSeek'));

		beforeEach(inject(function(_$controller_, _restService_, _$q_, _$rootScope_, _snackbarService_) {
			$controller = _$controller_;
			restService = _restService_;
			$q = _$q_;
			$rootScope = _$rootScope_;
			snackbarService = _snackbarService_;

			spyOn(restService, 'addProductToCollection').and.returnValue($q.when(DUMMY_PRODUCT));
			spyOn(restService, 'createCollection').and.returnValue($q.when(DUMMY_NEW_COLLECTION));
			spyOn(snackbarService, 'showSnackbar');
			spyOn(snackbarService, 'showSnackbars');
			spyOn(DUMMY_MODAL_INSTANCE, 'close');
			spyOn(DUMMY_MODAL_INSTANCE, 'dismiss');

			collectionModalCtrl = $controller('CollectionModalCtrl', {$scope: $scope, 
				$uibModalInstance: DUMMY_MODAL_INSTANCE, restService: restService, product: DUMMY_PRODUCT,
				collections: DUMMY_COLLECTION_OBJECT, snackbarService: snackbarService
			});
		}));

		describe('$scope.product', function() {
			it('should be defined', function() {
				expect($scope.product).toBeDefined();
			});

			it('should be defined with the resolved "product"', function() {
				expect($scope.product).toEqual(DUMMY_PRODUCT);
			});
		});

		describe('$scope.collections', function() {
			it('should be defined', function() {
				expect($scope.collections).toBeDefined();
			});

			it('should be defined with the given collections', function() {
				expect($scope.collections).toEqual(DUMMY_COLLECTION_OBJECT.collections);
			});

			it('should have collections containsProduct state which contain the given product as true', function() {
				expect($scope.collections[1].containsProduct).toBe(true);
			});

			it('should have collections containsProduct state which containt the given product as false', function() {
				expect($scope.collections[0].containsProduct).toBe(false);
			});
		})

		describe('$scope.showWell', function() {
			it('should be defined', function() {
				expect($scope.showWell).toBeDefined();
			});

			it('should be defined as false', function(){
				expect($scope.showWell).toBe(false);
			});

			it('should be true after $scope.toggleWell() is called', function() {
				$scope.toggleWell();
				expect($scope.showWell).toBe(true);
			});

			it('should be false after two calls of $scope.toggleWell()', function() {
				$scope.toggleWell();
				$scope.toggleWell();				
				expect($scope.showWell).toBe(false);
			});
		});

		describe('$scope.addToCollection()', function() {
			beforeEach(function() {
				$scope.addToCollection(DUMMY_COLLECTION);
			});

			it('should be defined', function() {
				expect($scope.addToCollection).toBeDefined();
			});

			it('should add product to the given collection', function() {
				var products = $scope.collections[0].products;
				expect(DUMMY_PRODUCT).toEqual(products[products.length-1]);
				expect($scope.collections[0].containsProduct).toBe(true);
			});

			it('should show the productAdded snackbar', function() {
				expect(snackbarService.showSnackbar).toHaveBeenCalledWith('productAdded');
			});

			it('should add the product to the given collection through the API', function() {
				expect(restService.addProductToCollection).toHaveBeenCalledWith(DUMMY_PRODUCT.id, DUMMY_COLLECTION.id);
			});

			it('should call close the modal with the given collection as parameter', function() {
				expect(DUMMY_MODAL_INSTANCE.close).toHaveBeenCalledWith(DUMMY_COLLECTION);
			});
		});

		describe('$scope.createAndAdd()', function() {
			beforeEach(function() {
				$scope.collectionName = DUMMY_NEW_COLLECTION.name;
				$scope.createAndAdd();
				$rootScope.$apply();
			});

			it('should be defined', function() {
				expect($scope.createAndAdd).toBeDefined();
			});

			it('should create the collection through the API', function() {
				expect(restService.createCollection).toHaveBeenCalledWith(DUMMY_NEW_COLLECTION.name);
			});

			it('should add the product to the new collection through the API', function() {
				expect(restService.addProductToCollection).toHaveBeenCalledWith(DUMMY_PRODUCT.id, DUMMY_NEW_COLLECTION.id);
			});

			it('should update the view with the new collection and with the new product inserted', function() {
				var collections = $scope.collections;
				var newCollection = collections[collections.length-1];
				expect(DUMMY_NEW_COLLECTION).toEqual(newCollection);
				expect(DUMMY_PRODUCT).toEqual(newCollection.products[0]);
				expect(newCollection.containsProduct).toBe(true);
			});

			it('should close the modal with the new collection as parameter', function() {
				expect(DUMMY_MODAL_INSTANCE.close).toHaveBeenCalledWith(DUMMY_NEW_COLLECTION);
			});

			it('should show the collection added and product added to collection snackbars', function() {
				expect(snackbarService.showSnackbars).toHaveBeenCalledWith(['collectionCreated','productAdded']);
				expect($scope.collectionAddedTo).toEqual(DUMMY_NEW_COLLECTION.name);
			});
		});

		describe('$scope.dismiss()', function() {
			beforeEach(function() {
				$scope.dismiss();
			});

			it('should be defined', function() {
				expect($scope.dismiss).toBeDefined();
			});

			it('should dismiss the modal with the "close" message', function() {
				expect(DUMMY_MODAL_INSTANCE.dismiss).toHaveBeenCalledWith('close');
			});
		});
	});
});