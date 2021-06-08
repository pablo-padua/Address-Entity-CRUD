# Address Entity CRUD
Developing a simple Address CRUD using Java, SpringBoot, Maven and PostgreSQL.

- Create method - Random valid address data generated using the <a href="https://geradornv.com.br/gerador-cep/">Gerador CEP</a> website.
  - Latitude and Longitude data taken from the <a href="https://developers.google.com/maps/documentation/geocoding/start">google maps api</a>.
- Find method - Finds an address entity based on the given id value and returns a JSON.
- Update method - Receives the entity's id and a Long param on the url request that updates the address' number.
- Delete method - Deletes an entity based on the given id.

<p>
  <img src="https://github.com/pablo-padua/Address-Entity-CRUD/blob/master/address-crud.gif">
</p>
