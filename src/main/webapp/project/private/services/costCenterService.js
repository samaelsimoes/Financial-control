/**
 * Arquivo Service Centro de custo  em angular.
 * @author Samael Pereira Simões
 */

app.factory('CostCenterService', function($resource) {
	return $resource('/control-expenses/rest/costcenter/', null, {
		update: {
			method: 'PUT'
		},
		remove: {
			method: 'DELETE'
		}
	});
});