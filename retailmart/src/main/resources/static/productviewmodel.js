
function Product(data) {
    var self = this;
    self.id  = (data ? ko.observable(data.id) : ko.observable());
    self.name = (data ? ko.observable(data.name) : ko.observable());
    self.price = (data ? ko.observable(data.price) : ko.observable());
    self.quantity =(data ? ko.observable(data.quantity) : ko.observable());
    self.selected = ko.observable(false);
    self.enableEdit = function () {
        if (!self.selected()) {
            self.selected(true);
        }
    }
}

var ViewModel = function() {

    var self = this;
    self.products = ko.observableArray();
    $.get("http://localhost:8080/product")
        .done(function(data){
            console.log(data);
            for (var key in data){
                self.products.push(new Product(data[key]));
            }
        })
        .fail(function (jqXHR, status, error) {
console.log(error)
        });

    self.idToFetch = ko.observable();
    self.selectedProduct = ko.observable();
    self.newProduct  = ko.observable();
    self.displayProduct = ko.observable();
    self.displayMessage = ko.observable();
    self.addProduct = function () {
        var  product = new Product();
        self.newProduct(product);
    };
    self.getProduct = function (){
        $.get("http://localhost:8080/product/"+self.idToFetch())
            .done(function(data){
                console.log(data);
                self.displayProduct(new Product(data));
                self.displayMessage("");
            })
            .fail(function (jqXHR, textStatus, errorThrown) {
                console.log(jqXHR.responseJSON);
                self.displayProduct(null);
                self.displayMessage(jqXHR.responseJSON.errorMessage);

            });
    };
    self.saveProduct = function (){
        console.log(JSON.stringify(self.newProduct()))

        $.ajax({
            url: "http://localhost:8080/product",
            contentType: "application/json",
            type:"POST",
            data: ko.toJSON(this)
        })
            .done(function(data){
                console.log(data);
                self.displayMessage("product created with id: "+data);
                self.newProduct().id(data);
                self.products.push(new Product(ko.toJS(self.newProduct())));
                self.newProduct(null);
            })
            .fail(function (jqXHR, status, error) {
                console.log(error);
                self.displayMessage(jqXHR.responseJSON.errorMessage);
            });
    };

    self.editProduct = function () {
        console.log(JSON.stringify(self.newProduct()))

        $.ajax({
            url: "http://localhost:8080/product",
            contentType: "application/json",
            type:"PUT",
            data: ko.toJSON(this),
            dataType:"json"
        })
            .done(function(data){
                console.log(data);
                self.displayMessage("product updated with id: "+data.id);
            })
            .fail(function (jqXHR, status, error) {
                console.log(error);
                self.displayMessage(jqXHR.responseJSON.errorMessage);
            });
    };

    self.deleteProduct = function () {
        console.log(JSON.stringify(self.newProduct()))
        var item = this;

        $.ajax({
            url: "http://localhost:8080/product/"+item.id(),
            type:"DELETE",
        })
            .done(function(data){
                console.log(data);
                self.displayMessage("product deleted with id: "+item.id());
                var id;
               $.each(self.products(),function(idx,val){
                   if(val.id() ==item.id()){
                       id = idx;
                   }

               })
                console.log(id);
                self.products.splice(id,1);
                if (self.products().length == 0) {
                    self.displayProduct(null);
                    self.displayMessage(null);
                }
            })
            .fail(function (jqXHR, status, error) {
                console.log(error);
                self.displayMessage(jqXHR.responseJSON.errorMessage);
            });
    };
};

ko.applyBindings(new ViewModel());