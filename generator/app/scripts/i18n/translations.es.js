'use strict';
define([], function() {

	return {
		WELCOME_MESSAGE: 'Always a pleasure scaffolding your apps',

		WELCOME_CONTROLLER: 'A new and shiny controller has been made!',
		WELCOME_CONTROLLER_FOLLOWUP: 'This is the default view for your controlller. Change it (if you want, of course)!',
        
        app: 'Apps',
        art: 'Arte',
        book: 'Libros',
        fashion: 'Moda',
        film: 'Películas',
        food: 'Comida',
        gadget: 'Electrónica',
        game: 'Juegos',
        music: 'Música',
        other: 'Otros',
        all: 'Todo',
        
        'description.app': 'Aquí encontrarás las mejores apps para todo lo que necesites.',
        'description.art': 'Si buscas inspiración, este es el lugar indicado. Tómate un momento para que tus emociones fluyan.',
        'description.book': 'No hay nada mejor que un buen libro. Expande tu conocimiento o déjate llevar por una gran aventura.',
        'description.fashion': '¿Quieres saber acerca de las últimas tendencias de moda? Entonces estás en la categoría indicada.',
        'description.film': 'Aquí encontrarás películas, cortos y mucho más! No te decepcionarás, lo prometemos.',
        'description.food': '¿Quieres probar algo nuevo? Aquí encontrarás fantásticos platos que te asombrarán.',
        'description.gadget': 'Echa un vistazo a la última tecnología de punta. ¡No puedes perderte esto!',
        'description.game': 'Encuentra algo nuevo y emocionante para jugar en casa o en el camino. El trabajo puede esperar. Diviértete.',
        'description.music': '¿Estás teniendo un día duro? Es hora de escuchar música que te hace sentir como el mejor.',
        'description.other': '¿Ninguna categoría corresponde con el producto que buscas? Puede que lo encuentres aquí.',
        'description.all': 'Aquí encontrarás productos de cualquier categoría.',
		
		'navbar.mostPopular':'Más populares',
		'navbar.signup':'Registrarse',
		'navbar.signin':'Ingresar',
		'navbar.post':'Publicar Producto',
		'navbar.logout':'Cerrar Sesión',
		'navbar.search':'Buscar',
		
		'modal.signIn.signToAccount':'Ingresa a tu cuenta',
		'modal.signIn.usernamePlaceholder':'hola@productseek.com',
		'modal.signIn.passwordPlaceholder':'Contraseña',
		'modal.signIn.rememberMe':'Recuérdame',
		'modal.signIn.signIn':'Iniciar sesión',
		
		'searchResults.products':'Productos',
		'searchResults.users':'Usuarios',
		'searchResults.resultsFor': 'Resultados de búsqueda para ',
		'searchResults.filterByCategory': 'Filtros',
		'searchResults.resetFilters': 'Reestablecer filtros',
		
		'searchZRP.notFound':'\uD83D\uDD0E',
		'searchZRP.tryDifferentSearch':'Intenta una búsqueda diferente',
		'searchZRP.noUsers':'No se encontraron usuarios que contengan {{keyword}}',
		'searchZRP.noProducts':'No se encontraron productos que contengan {{keyword}}',
        
        mostRecent: 'Productos más recientes',
        allProducts: 'Todos los productos',
        recent: 'Recientes',
        popular: 'Más Votados',
        atoz: 'a-z',

        'Profile.Tab.uploadedProducts': 'Publicados',
        'Profile.Tab.votedProducts': 'Votados',
        'Profile.Tab.favlist': 'Colecciones',
        'Profile.settings.changePicture':'Cambiar foto de perfil',
        'Profile.settings.changePassword': 'Cambiar contraseña',
        'userFormLabel.passwordConfirm': 'Confirmar Contraseña',
    
        'changePassword.currentPasswordConf': 'Contraseña Actual',
        'changePassword.newPassword':'Nueva Contraseña',
        'changePassword': 'Cambiar Contraseña',
        'passwordForm.cancel': 'Cancelar',

        'userFormLabel.newProfilePicture': 'Nueva Foto de Perfil',

        'Profile.modal.deleteProduct': 'Eliminar Producto',
        'Profile.modal.textBeginning': '¿Estás seguro de que deseas eliminar',
        'Profile.modal.textEnd': '? Esta acción no puede ser revertida.',
        'Profile.modal.leftButton': 'Eliminar',
        'Profile.modal.rightButton': 'Cancelar',

        'Profile.Tab.uploadedProducts': 'Publicados',
        'Profile.Tab.votedProducts': 'Votados',
        'Profile.Tab.favlist': 'Colecciones',
        'Profile.settings.changePicture':'Cambiar foto de perfil',
        'Profile.settings.changePassword': 'Cambiar contraseña',
        
        'collection.collectionNamePlaceholder': 'Nombre',
        'collections.newCollection': 'Nueva Colección',
        'collections.add': 'Agregar',
        'collections.toCollection': 'a una Colección',
        'collections.noCollectionsYet': 'No has creado ninguna Colección.',
        'collections.createAndAdd': 'Crear Colección y agregar',
        'collections.addInNewCollection': 'Agregar en una nueva Colección',
        'collections.createCollection': 'Crear nueva Colección',
        'collections.create': 'Crear',
        'collections.alreadyInCollection': 'Ya está en la Colección',
        
        'ownerUserZRP.noFavLists': 'No has creado ninguna Colección aún.',
		
		'post.postProduct': 'Publicar producto',
		'post.giveUsInformation': 'Danos información de tu producto',
		'post.productName': 'Nombre del producto',
		'post.placeholder.productName': 'Product Seek',
		'post.tagline': 'Eslogan',
		'post.placeholder.tagline': 'Una descripción rápida del producto',
		'post.description': 'Descripción',
		'post.placeholder.description': 'Descripción completa',
		'post.webpageOptional': 'Página Web (Opcional)',
		'post.placeholder.webpage': 'www.productseek.com',
		'post.category': 'Categoría',
		'post.addImageOrVideo': 'Agrega al menos una imagen o video',
        'post.videos': 'Videos de Youtube',
		'post.link': 'Link',
		'post.postButton': 'Publicar',
		
        'button.addLogo':'Agregar Logo',
		'button.addImage':'Agregar Imagen',
		
        'product.singular': 'producto',
        'product.plural': 'productos',
        
        'collection.error.nameTooShort': 'Nombre de la Colección debe por lo menos contener 4 caracteres',
        'collection.error.nameTooLong': 'Nombre de la Colección no puede exceder los 64 caracteres',
        
        'feedback.favListCreated.textEnd': 'fue creada',
        'feedback.productAddedToFavList.text': 'fue agregado a'
	};
});
