'use strict';
define(['productSeek', 'jquery', 'services/sessionService'], function(productSeek) {
		return productSeek.factory('restService', ['$http', 'url', 'sessionService', function($http, url, session) {
            
            var translateTable = {
                    category: 'category',
                    page: 'page',
                    pageSize: 'per_page',
                    orderBy: 'sorted_by',
                    order: 'order',
                    query: 'q'
            };

            function translate(params) {
                var translated = {};
                
                if (params) {
                    jQuery.each(params, function(key, value) {
                        translated[translateTable[key]] = value;
                    });
                }
                
                return translated;
            }
			
            function authHeaders() {
                var accessToken = session.getAccessToken();
                return accessToken ? {headers: {'X-AUTH-TOKEN': accessToken}} : undefined;
            }
            
            function multipartMetadata() {
                return {
                    transformRequest: angular.identity,
                    headers: {
                        'Content-Type': undefined,
                        'X-AUTH-TOKEN': session.getAccessToken()                       
                    }
                };
            }
            
            function doPost(baseUrl, data, params) {
            	var params = translate(params);
 				params = Object.keys(params).length ? '?' + jQuery.param(params) : '';
 
                 return $http.post(baseUrl + params, JSON.stringify(data), authHeaders())
                        .then(function(response) {
                             return response.data;
                        })
                        .catch(function(response) {
                             return response.data;
                        });                
            }
            
			function doGet(baseUrl, params) {
                var params = translate(params);
				params = Object.keys(params).length ? '?' + jQuery.param(params) : '';
                
				return  $http.get(baseUrl + params, authHeaders())
						.then(function(response) {
							return response.data;
						})
						.catch(function(response) {
							return response.data;
						});
			}
            
            function doPut(baseUrl, data, params) {
                var params = translate(params);
				params = Object.keys(params).length ? '?' + jQuery.param(params) : '';

                return $http.put(baseUrl + params, JSON.stringify(data), authHeaders())
                        .then(function(response) {
                            return response.data;
                        })
                        .catch(function(response) {
                            return response.data;
                        });
            }

            function doDelete(baseUrl, params) {
                var params = translate(params);
				params = Object.keys(params).length ? '?' + jQuery.param(params) : '';

                return $http.delete(baseUrl + params, authHeaders())
                        .then(function(response) {
                            return response.data;
                        })
                        .catch(function(response) {
                            return response.data;
                        });
            }            
            
			return {
				getProducts: function(params) {
					return doGet(url + '/products', params);
				},
                
				getProduct: function(id) {
					return doGet(url + '/products/' + id);
				},
                
				getComments: function(id, params) {
					return doGet(url + '/products/' + id + '/comments', params);
				},
                
				getCollectionsForUser: function(id, params) {
					return doGet(url + '/users/' + id + '/collections', params);
				},
                
				getUser: function(id) {
					return doGet(url + '/users/' + id);
				},
                
				getVotedByUser: function(id, params) {
					return doGet(url + '/users/' + id + '/voted_products', params);
				},
                
				getPostedByUser: function(id, params) {
					return doGet(url + '/users/' + id + '/created_products', params);
				},
                
				getProductVoters: function(id, params) {
					return doGet(url + '/product/' + id + '/voters', params);
				},
                
				searchProducts: function(query) {
					return doGet(url + '/search/products', {query: query});
				},
                
				searchUsers: function(query) {
					return doGet(url + '/search/users', {query: query});
				},
                
                voteProduct: function(id) {
                    return doPut(url + '/products/' + id + '/votes');
                },

                addProductToCollection: function(productId, collectionId) {
                    return doPut(url + '/collections/' + collectionId + '/products/' + productId);
                },
                
                unvoteProduct: function(id) {
                    return doDelete(url + '/products/' + id + '/votes');
                },
                
                deleteProduct: function(id) {
                    return doDelete(url + '/products/' + id);
                },
                
                createCollection: function(name) {
                    return doPost(url + '/collections', {'name': name});
                },

                changePassword: function(currentPass, newPass) {
                    return doPut(url + '/user/password', 
                            {'current_password': currentPass, 'new_password': newPass});
                },
                
                createUser: function(data) {
                    var userData = {name: data.name, password: data.password, email: data.email};
                    var picture = data.picture;
                    var formData = new FormData();
                    
                    formData.append('picture', picture);
                    formData.append('user', JSON.stringify(userData));
                    
                    return $http.post(url + '/users', formData, multipartMetadata())
                    .then(function(response) {
                        return response.data;
                    })
                    .catch(function(response) {
                        return response.data;
                    })
                },
                
                postProduct: function(data) {
                    var productData = {name: data.name, tagline: data.tagline, description: data.description, category: data.category, video_ids: data.videos};
                    var logo = data.logo;
                    var images = data.images;
                    var formData = new FormData();
                    
                    angular.forEach(images, function(img) {
                        formData.append('picture', img);
                    });
                    
                    formData.append('logo', logo);
                    formData.append('product', JSON.stringify(productData));
                    
                    return $http.post(url + '/products', formData, multipartMetadata())
                    .then(function(response) {
                        return response.data;
                    })
                    .catch(function(response) {
                        return response.data;
                    });
                }
			}
		}]);
	}
);

