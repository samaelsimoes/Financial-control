/**
 * Arquivo service tipo de custo em angular.
 * @author Samael Pereira Simões
 */
app.factory('GenreService', function($resource) {	
	return $resource('/control-expenses/rest/genre/', null, {
	    update: {
	    	method: 'PUT'
	    },
	    remove: {
			method: 'DELETE'
		}
	});
});

