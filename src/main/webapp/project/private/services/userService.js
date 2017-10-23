/**
 * Arquivo service user em angular.
 * @author Samael Pereira Simões
 */
app.factory('UserService', function($resource) {
	return $resource('/control-expenses/rest/user/', null, {
		update: {
			method: 'PUT'
		},
		remove: {
			method: 'DELETE'
		}
	});
});