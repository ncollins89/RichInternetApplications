//------------------------------ ROOT URL Function -------------------------------------
var rootURL = "http://localhost:8080/NcCars/rest/cars";

var findAll=function(){
	$.ajax({
		type:'GET', 
		url:rootUrl,
		dataType: "json",
		success:renderList});		
};
//------------------------------Function For the Directory tab panel -------------------
var TableView = Backbone.View.extend({
  collection: CarList,
  initialize: function(){
   this.listenTo(this.collection, 'add', this.renderList);
   this.render();
   $('#myTable').dataTable();
  },

  render: function(){
	  $('#myTBody tr').remove();
	  this.collection.each(function(car){	  		
		  $('#myTBody').append(new CarView({model:car}).render());
	  }, this);
  }
});

//----------------------------------------Function for the Car List tab--------------------------------
var CarView = Backbone.View.extend({
  collection: CarList,
  initialize: function(){
   this.render();
   $('#myCarTable').dataTable();
  },

  render: function(){
	  var carModel = $('#Carlist').val()
	  $('#myCarTBody tr').remove();
	  this.collection.each(function(car){
		  if(car.get("model") == carModel){
			  $('#myCarTBody').append(new fCarView({model:car}).render())
		  }
		  else
			  console.log("Not Featured");
	  }, this);
  }
});
//--------------------------------------- Models -----------------------------------------
var rootURL = "http://localhost:8080/NcCars/rest/cars";

var Cars = Backbone.Model.extend({
	urlRoot:rootURL,
	defaults:{       
		"id":null, 
		"make":"",     
		"model":"",  
		"country":"ireland",  
		"year":"",      
		"description":"",  
		"picture":"generic.jpg"},
  initialize: function(){
    console.log("car init");
  }
});

var CarList = Backbone.Collection.extend({
	model: Car,
	url:rootURL});