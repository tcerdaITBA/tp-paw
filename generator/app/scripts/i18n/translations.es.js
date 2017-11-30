'use strict';
define([], function() {

	return {
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
        
        'title.search': 'Resultados de Búsqueda',
        'title.post': 'Publicar',

        'date': 'Recientes',
        'alpha': 'a-z',
        'votes': 'Más Votados',
        
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

        'categoryZRP.sorry': '\uD83D\uDE14',
        'categoryZRP.noProducts': 'Aún no hay productos en esta categoría.',
		
	'navbar.mostPopular':'Más populares',
	'navbar.signup':'Registrarse',
	'navbar.signin':'Ingresar',
	'navbar.post':'Publicar Producto',
	'navbar.logout':'Cerrar Sesión',
	'navbar.search':'Buscar',
	'navbar.searchHistory': 'Historial de búsqueda',
	
	'modal.signIn.signToAccount':'Ingresa a tu cuenta',
	'modal.signIn.usernamePlaceholder':'hola@productseek.com',
	'modal.signIn.passwordPlaceholder':'Contraseña',
	'modal.signIn.rememberMe':'Recuérdame',
	'modal.signIn.signIn':'Iniciar sesión',
        'modal.signIn.error.invalidUser': 'Email o contraseña inválidos',
		
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

        'popover.toPost': 'Para publicar un producto debes',
        'popover.toVote': 'Para votar un producto debes',
        'popover.toComment': 'Para comentar un producto debes',
        'popover.signIn': 'iniciar sesión',
        'popover.toPostBis': 'o',
        'popover.signUp': 'registrarte.',

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
        'Profile.modal.collectionTextBeginning': '¿Estás seguro que deseas eliminar la colecciòn ',
        'Profile.modal.textEnd': 'Esta acción no puede ser revertida.',
        'Profile.modal.leftButton': 'Eliminar',
        'Profile.modal.rightButton': 'Cancelar',
        
                
        'Profile.modal.deleteCollection': 'Eliminar colección',

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

	'post.error.addImageOrVideo': 'Agrega al menos una imagen o video',
	'post.error.invalidYoutube': 'Link de Youtube inválido',
	'post.error.addTagline': 'El eslogan no puede estar vacío',
        'post.error.taglineTooLong': 'El eslogan no puede exceder los 140 caracteres',
	'post.error.addName': 'El nombre del producto no puede estar vacío',
        'post.error.nameTooShort': 'El nombre del producto debe contener al menos 4 caracteres',
        'post.error.nameTooLong': 'El nombre del producto no puede exceder los 64 caracteres',
	'post.error.addDescription': 'La descripción no puede estar vacía',
        'post.error.descriptionTooLong': 'La descripción no puede exceder los 8000 caracteres',
        'post.error.addLogo': 'El logo no puede estar vacío',
        'post.error.invalidWebpage': 'La página web no es válida',
        
        'button.addLogo':'Agregar Logo',
	'button.addImage':'Agregar Imagen',
		
        'product.singular': 'producto',
        'product.plural': 'productos',
        
        'collection.error.nameTooShort': 'El nombre de la Colección debe contener al menos 4 caracteres',
        'collection.error.nameTooLong': 'El nombre de la Colección no puede exceder los 64 caracteres',
        
        'feedback.favListCreated.textEnd': 'fue creada',
        'feedback.productAddedToFavList.text': 'fue agregado a',
        'feedback.productDeleted.textEnd': 'fue eliminado',

        'feedback.passwordChanged.text' : 'Contraseña cambiada exitosamente',
        'feedback.pictureChanged.text' : 'Foto de perfil cambiada exitosamente',

        'feedback.warning.noConnection': 'Oops, parece que has perdido la conexión',
        
        'button.addProfilePicture' : 'Foto de Perfil',
        'modal.signUp.title': 'Registrarse',
        'modal.signUp.passwordConfPlaceholder': 'Confirmar Contraseña',
        'modal.signUp.usernamePlaceholder': 'Nombre',
        'modal.signUp.emailPlaceholder': 'Email',
        'modal.signUp.signUp': 'Registrarse',
        
        'modal.password.notMatchOldPassword' : 'La contraseña ingresada no coincide con la actual',
        
        'signUp.error.addPicture': 'Agregue imagen de perfil',
        'signUp.error.addName': 'El nombre no puede estar vacío',
        'signUp.error.nameTooLong': 'El nombre no puede exceder los 30 caracteres',
        'signUp.error.nameTooShort': 'El nombre debe contener al menos 4 caracteres',
        'signUp.error.addPassword': 'La contraseña no puede estar vacía',
        'signUp.error.passwordTooLong': 'La contraseña no puede exceder los 60 caracteres',
        'signUp.error.passwordTooShort': 'La contraseña debe contener al menos 6 caracteres',
        'signUp.error.passwordMatch': 'Las contraseñas deben ser iguales',
        'signUp.error.addEmail': 'El no puede estar vacío',
        'signUp.error.invalidEmail': 'El email provisto no es válido',
	'signIn.error.addEmail': 'Indica tu email',
	'signIn.error.addPassword': 'Indica tu contraseña',

        'modal.changePicture.empty': 'Agregue imagen',

        'product.creator':'Creador',
        'product.comment':'Comentar',
        'product.comment.placeholder':'Escriba su comentario aqui',
        'product.comment.error':'La longitud del comentario debe ser menor o igual a 512',
        'product.discussion':'Únete a la discusión',
        'product.anonymousComment.beginning':'Para comentar un producto debes',
        'product.anonymousComment.signIn':'iniciar sesión',
        'product.anonymousComment.or':'o',
        'product.anonymousComment.signUp':'registrarte.',
        'ZRP.noProductsInCollection': 'No hay productos en esta colección'

	};
});
