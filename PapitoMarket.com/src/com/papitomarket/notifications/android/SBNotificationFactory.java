package com.papitomarket.notifications.android;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.Iterator;

import android.app.Activity;
import android.content.Intent;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.papitomarket.service.android.Updates;

public class SBNotificationFactory implements JsonDeserializer<SBNotification>{

	
	
	
	private Gson gson;
	
    private String type;
	private String body;
	
	/*
	 *    Notification examples: 
	 * 
	 * 
	 *    SBAnnouncementNotification: 
	 * 
	 *    { "type": "SBAnnouncementNotification",
	 *      "body":  { "message": "my sample announcement... we will release a new version.. promotions.. etc"  }
	 *    }
	 *     
	 *     
	 *     SBChargeCreditNotification:
	 *     
	 *    { "type": "SBChargeCreditNotification",
	 *      "body": "Not enough funds, charge credit in order to see your next order" }
	 * 
	 * 
	 *     SBQuestionNotification:
	 * 
	 *    { "type": "SBQuestionNotification",
	 *      "body": { "question": " Are those red hat smaller than the ones I saw on TV? ",
	 *      "from": "pablo.borda-fb@192.241.140.67 }
	 * 
	 *     SBSoldNotification:
	 * 
	 *    { "type": "SBSoldNotification",
	 *      "body": [{ "id" : "124",
	 *                 "name":"Carne Suave",
	 *                 "addr": "Avenida Corrientes 3722, Buenos Aires, Autonomous City of Buenos Aires, Argentina",
	 *                 "amount": "6",
	 *                 "total":"27.0",
	 *                 "who":"PabloBordas-fb"},
	 *                {"id" : "125",
	 *                 "name":"Carne picante",
	 *                 "addr": "Avenida Corrientes 3722, Buenos Aires, Autonomous City of Buenos Aires, Argentina",
	 *                 "amount": "9",
	 *                 "total":"40.5",
	 *                 "who":"PabloBordas-fb"},
	 *                {"id" : "128",
	 *                 "name":"Carne saltena",
	 *                 "addr": "Avenida Corrientes 3722, Buenos Aires, Autonomous City of Buenos Aires, Argentina",
	 *                 "amount": "4",
	 *                 "total":"18.0",
	 *                 "who":"PabloBordas-fb"}]}
	 * 
	 *   
	 *   SBTagNotification:
	 * 
	 *   { "type": "SBTagNotification",
	 *     "body": { "tag" : "empanada",
	 *               "distance" : "300mt",
	 *               "results": [{"store":[223,"A los cuatro quesos","showstore",68.0,21,"Morita Empanadas","logo-moritaempanadas.jpg",2.4363460081848847,-34.6034,-58.4184],"products":{"empanada":[{"product":{"category_id":null,"company_id":21,"description":"Muzarella de primera calidad, queso sardo, provolone, regianito, suavemente condimentados. ","id":193,"name":"A los cuatro quesos","orders":0,"pictures":[],"price":4.0,"running":null,"superproduct_id":66}},{"product":{"category_id":null,"company_id":21,"description":"Atun de primera marca, saltado con cebollas, morrones y agregado de huevo duro, finamente condimentado por nuestro Chef.","id":194,"name":"Atun","orders":0,"pictures":[],"price":4.0,"running":null,"superproduct_id":66}},{"product":{"category_id":null,"company_id":21,"description":"Muzarella y calabaza saborizado con finas hierbas.","id":195,"name":"Calabaza","orders":0,"pictures":[],"price":4.0,"running":null,"superproduct_id":66}},{"product":{"category_id":null,"company_id":21,"description":"Muzarella de primera calidad, tomates frescos y albaca de la quinta.","id":196,"name":"Caprese","orders":0,"pictures":[],"price":4.0,"running":null,"superproduct_id":66}},{"product":{"category_id":null,"company_id":21,"description":"Roast beef cortado a cuchillo, totalmente desgrasada, cebolla, aji, morron, huevo duro, condimentos y verdeo.","id":197,"name":"Carne cortada a cuchillo","orders":0,"pictures":[],"price":4.0,"running":null,"superproduct_id":66}},{"product":{"category_id":null,"company_id":21,"description":"Carne picada de roast beef previamente desgrasada, cebolla, aji morron, huevo duro, pimenton, aji molido, pimienta tricolor, pimienta de cayena y un toque de verdeo.","id":198,"name":"Carne Picante","orders":0,"pictures":[],"price":4.0,"running":null,"superproduct_id":66}},{"product":{"category_id":null,"company_id":21,"description":"Carne picada de roast beef previamente desgrasada, cebolla, aji morron, huevo duro, condimentos y un toque de verdeo.","id":199,"name":"Carne Suave","orders":0,"pictures":[],"price":4.0,"running":null,"superproduct_id":66}},{"product":{"category_id":null,"company_id":21,"description":"Muzarella de primera calidad, longaniza, y varios condimentos.","id":200,"name":"Diabla","orders":0,"pictures":[],"price":4.0,"running":null,"superproduct_id":66}},{"product":{"category_id":null,"company_id":21,"description":"Choclo amarillo seleccionado, saltado con verdeo y unido con una rica salsa blanca.","id":201,"name":"Humita","orders":0,"pictures":[],"price":4.0,"running":null,"superproduct_id":66}},{"product":{"category_id":null,"company_id":21,"description":"Muzarella de primera calidad, jamon cocido y condimentos.","id":202,"name":"Jamon y Queso","orders":0,"pictures":[],"price":4.0,"running":null,"superproduct_id":66}},{"product":{"category_id":null,"company_id":21,"description":"Panceta aumada, muzzarella, ciruelas.","id":203,"name":"Panceta y Ciruelas","orders":0,"pictures":[],"price":4.0,"running":null,"superproduct_id":66}},{"product":{"category_id":null,"company_id":21,"description":"Pechuguitas de pollo, aji, morron, cebolla, huevo duro y condimentos.","id":204,"name":"Pollo","orders":0,"pictures":[],"price":4.0,"running":null,"superproduct_id":66}},{"product":{"category_id":null,"company_id":21,"description":"Pechuga en cubitos, cebolla, salsa blanca, saborizado con finas hierbas.","id":205,"name":"Pollo blanco","orders":0,"pictures":[],"price":4.0,"running":null,"superproduct_id":66}},{"product":{"category_id":null,"company_id":21,"description":"Mozarella de primera calidad, con agregado de verdeo fresco y varios condimentos.","id":206,"name":"Queso al verdeo","orders":0,"pictures":[],"price":4.0,"running":null,"superproduct_id":66}},{"product":{"category_id":null,"company_id":21,"description":"Mozarella de primera calidad, jamon cocido, queso roquefort, y condimentos.","id":207,"name":"Roquefort con jamon","orders":0,"pictures":[],"price":4.0,"running":null,"superproduct_id":66}},{"product":{"category_id":null,"company_id":21,"description":"Muzarella, queso roquefort y apio fresco.","id":208,"name":"Roquefort y Apio","orders":0,"pictures":[],"price":4.0,"running":null,"superproduct_id":66}},{"product":{"category_id":null,"company_id":21,"description":"Espinacas de la huerta, saltada con verdeo y unida a una rica salsa blanca.","id":209,"name":"Verdura","orders":0,"pictures":[],"price":4.0,"running":null,"superproduct_id":66}}],"tartin":[{"product":{"category_id":null,"company_id":21,"description":"Muzarella de primera calidad, jamon cocido, y anana de Brasil.","id":210,"name":"Doble de Anana","orders":0,"pictures":[],"price":8.0,"running":null,"superproduct_id":67}},{"product":{"category_id":null,"company_id":21,"description":"Muzarella de primera calidad, jamon cocido, huevo duro, y cebolla de verdeo.","id":211,"name":"Doble de Cebolla y Huevo","orders":0,"pictures":[],"price":8.0,"running":null,"superproduct_id":67}},{"product":{"category_id":null,"company_id":21,"description":"Muzarella de primera calidad, jamon cocido, palmitos de Brasil, y morrones frescos.","id":212,"name":"Doble de Palmito y Morron","orders":0,"pictures":[],"price":8.0,"running":null,"superproduct_id":67}},{"product":{"category_id":null,"company_id":21,"description":"Muzarella de primera calidad, jamon cocido, huevo duro y tomates frescos.","id":213,"name":"Doble de Tomate y Huevo","orders":0,"pictures":[],"price":8.0,"running":null,"superproduct_id":67}}],"pizza":[{"product":{"category_id":null,"company_id":21,"description":"Muzarella, salsa de tomate casera, oregano y aceitunas verdes.","id":214,"name":"Muzzarella","orders":0,"pictures":[],"price":8.0,"running":null,"superproduct_id":74}},{"product":{"category_id":null,"company_id":21,"description":"Salsa de tomate casera, anchoas, oregano y aceitunas verdes.","id":215,"name":"Anchoas","orders":0,"pictures":[],"price":8.0,"running":null,"superproduct_id":74}},{"product":{"category_id":null,"company_id":21,"description":"Salsa de tomate casera, muzarella, oregano, aji molido, rodajas de salame picado grueso y aceitunas verdes.","id":216,"name":"Calabresa","orders":0,"pictures":[],"price":8.0,"running":null,"superproduct_id":74}},{"product":{"category_id":null,"company_id":21,"description":"Cebolla cortada pluma, oregano y aceitunas verdes.","id":217,"name":"Fugazza","orders":0,"pictures":[],"price":8.0,"running":null,"superproduct_id":74}},{"product":{"category_id":null,"company_id":21,"description":"Cebolla cortada pluma, muzarella, queso roquefort, oregano y aceitunas verdes.","id":218,"name":"Fugazza al roquefort","orders":0,"pictures":[],"price":8.0,"running":null,"superproduct_id":74}},{"product":{"category_id":null,"company_id":21,"description":"Cebolla cortada pluma, muzarella, oregano y aceitunas verdes.","id":219,"name":"Fugazzeta","orders":0,"pictures":[],"price":8.0,"running":null,"superproduct_id":74}},{"product":{"category_id":null,"company_id":21,"description":"Salsa de tomate casera, muzarella, jamon cocido en fetas, oregano y aceitunas verdes.","id":220,"name":"Jamon","orders":0,"pictures":[],"price":8.0,"running":null,"superproduct_id":74}},{"product":{"category_id":null,"company_id":21,"description":"Salsa de tomate casera, muzarella, jamon cocido en fetas, morrones en tira, oregano y aceitunas verdes.","id":221,"name":"Jamon y morron","orders":0,"pictures":[],"price":8.0,"running":null,"superproduct_id":74}},{"product":{"category_id":null,"company_id":21,"description":"Salsa de tomate casera, muzarella, jamon cocido en fetas, huevo duro picado, oregano y aceitunas verdes.","id":222,"name":"Jamon y huevo","orders":0,"pictures":[],"price":8.0,"running":null,"superproduct_id":74}},{"product":{"category_id":null,"company_id":21,"description":"Salsa de tomate casera, muzarella, jamon cocido en fetas, palmitos, salsa golf, oregano, y aceitunas verdes.","id":223,"name":"Jamon y palmitos","orders":0,"pictures":[],"price":8.0,"running":null,"superproduct_id":74}},{"product":{"category_id":null,"company_id":21,"description":"Salsa de tomate casera, muzarella, jamon cocido en fetas,anana en rodajas, oregano y aceitunas verdes.","id":224,"name":"Jamon y anana","orders":0,"pictures":[],"price":8.0,"running":null,"superproduct_id":74}},{"product":{"category_id":null,"company_id":21,"description":"Salsa de tomate casera, muzarella, rodajas de tomate, queso sardo rallado, provenzal, oregano y aceitunas verdes.","id":225,"name":"Napolitana","orders":0,"pictures":[],"price":8.0,"running":null,"superproduct_id":74}},{"product":{"category_id":null,"company_id":21,"description":"Salsa de tomate casera, muzarella,jamon cocido en fetas, rodajas de tomate, queso sardo rallado, provenzal, oregano y aceitunas verdes.","id":226,"name":"Napolitana con jamon","orders":0,"pictures":[],"price":8.0,"running":null,"superproduct_id":74}},{"product":{"category_id":null,"company_id":21,"description":"Salsa de tomate casera, muzarella,queso roquefort, oregano y aceitunas verdes.","id":227,"name":"Roquefort","orders":0,"pictures":[],"price":8.0,"running":null,"superproduct_id":74}}]}},{"store":[142,"Carne Suave","showstore",117.0,19,"El noble repulgue","logo-noblerepulgue.jpg",2.5531374941472116,-34.6061,-58.4213],"products":{"empanada":[{"product":{"category_id":null,"company_id":19,"description":"Empanada de Carne Suave","id":124,"name":"Carne Suave","orders":0,"pictures":[{"picture":{"company_id":19,"id":228,"product_id":124,"url":"/empanada.jpg"}},{"picture":{"company_id":19,"id":229,"product_id":124,"url":"/empanadas_santiaguenas.jpg"}},{"picture":{"company_id":19,"id":231,"product_id":124,"url":"/sweet-empanada-dough.jpg"}}],"price":4.5,"running":null,"superproduct_id":66}},{"product":{"category_id":null,"company_id":19,"description":"Empanada de Carne picante ","id":125,"name":"Carne picante","orders":0,"pictures":[],"price":4.5,"running":null,"superproduct_id":66}},{"product":{"category_id":null,"company_id":19,"description":"Empanada de Carne cortada a cuchillo","id":126,"name":"Carne cortada a cuchillo","orders":0,"pictures":[],"price":4.5,"running":null,"superproduct_id":66}},{"product":{"category_id":null,"company_id":19,"description":"Carne con aceituna y huevo","id":127,"name":"Carne con aceituna y huevo ","orders":0,"pictures":[],"price":4.5,"running":null,"superproduct_id":66}},{"product":{"category_id":null,"company_id":19,"description":"Empanada de Carne saltena","id":128,"name":"Carne saltena","orders":0,"pictures":[],"price":4.5,"running":null,"superproduct_id":66}},{"product":{"category_id":null,"company_id":19,"description":"Empanada de Carne dulce ","id":129,"name":"Carne dulce ","orders":0,"pictures":[],"price":4.5,"running":null,"superproduct_id":66}},{"product":{"category_id":null,"company_id":19,"description":"Empanada de Carne con hongos ","id":130,"name":"Carne con hongos ","orders":0,"pictures":[],"price":4.5,"running":null,"superproduct_id":66}},{"product":{"category_id":null,"company_id":19,"description":"Empanada de Jam\u00c3\u00b3n y queso ","id":131,"name":"Jam\u00c3\u00b3n y queso ","orders":0,"pictures":[],"price":4.5,"running":null,"superproduct_id":66}},{"product":{"category_id":null,"company_id":19,"description":"Empanada de Muzzarella","id":132,"name":"Jam\u00c3\u00b3n y queso ","orders":0,"pictures":[],"price":4.5,"running":null,"superproduct_id":66}},{"product":{"category_id":null,"company_id":19,"description":"Empanada de Muzzarella","id":133,"name":"Muzarella","orders":0,"pictures":[],"price":4.5,"running":null,"superproduct_id":66}},{"product":{"category_id":null,"company_id":19,"description":"Empanada de Queso y cebolla","id":134,"name":"Queso y cebolla","orders":0,"pictures":[],"price":4.5,"running":null,"superproduct_id":66}},{"product":{"category_id":null,"company_id":19,"description":"Empanada de Pollo","id":135,"name":"Pollo","orders":0,"pictures":[],"price":4.5,"running":null,"superproduct_id":66}},{"product":{"category_id":null,"company_id":19,"description":"Pollo con hierbas","id":136,"name":"Pollo con hierbas","orders":0,"pictures":[],"price":4.5,"running":null,"superproduct_id":66}},{"product":{"category_id":null,"company_id":19,"description":"Empanada de Espinaca c/salsa blanca","id":137,"name":"Espinaca c/salsa blanca","orders":0,"pictures":[],"price":4.5,"running":null,"superproduct_id":66}},{"product":{"category_id":null,"company_id":19,"description":"Empanada de Espinaca c/queso ","id":138,"name":"Espinaca c/queso ","orders":0,"pictures":[],"price":4.5,"running":null,"superproduct_id":66}},{"product":{"category_id":null,"company_id":19,"description":"Empanada de Salteado de vegetales ","id":139,"name":"Salteado de vegetales ","orders":0,"pictures":[],"price":4.5,"running":null,"superproduct_id":66}},{"product":{"category_id":null,"company_id":19,"description":"Empanada de Humita","id":140,"name":"Humita","orders":0,"pictures":[],"price":4.5,"running":null,"superproduct_id":66}},{"product":{"category_id":null,"company_id":19,"description":"Empanada de Atun","id":141,"name":"Atun","orders":0,"pictures":[],"price":4.5,"running":null,"superproduct_id":66}},{"product":{"category_id":null,"company_id":19,"description":"Empanada de Roquefort con jamon","id":142,"name":"Roquefort con jamon","orders":0,"pictures":[],"price":4.5,"running":null,"superproduct_id":66}},{"product":{"category_id":null,"company_id":19,"description":"Empanada de Champignon con queso y jerez","id":143,"name":"Champignon con queso y jerez","orders":0,"pictures":[],"price":4.5,"running":null,"superproduct_id":66}},{"product":{"category_id":null,"company_id":19,"description":"Empanada de Panceta, morron y mozzarella","id":144,"name":"Panceta, morron y mozzarella","orders":0,"pictures":[],"price":4.5,"running":null,"superproduct_id":66}},{"product":{"category_id":null,"company_id":19,"description":"Empanada de Panceta, ciruela y mozzarella","id":145,"name":"Panceta, ciruela y mozzarella","orders":0,"pictures":[],"price":4.5,"running":null,"superproduct_id":66}},{"product":{"category_id":null,"company_id":19,"description":"Empanada de Jamon, queso, huevo y cebolla ","id":146,"name":"Jamon, queso, huevo y cebolla ","orders":0,"pictures":[],"price":4.5,"running":null,"superproduct_id":66}},{"product":{"category_id":null,"company_id":19,"description":"Empanada de Patag\u00c3\u00b3nica de cordero ","id":147,"name":"Patagonica de cordero ","orders":0,"pictures":[],"price":4.5,"running":null,"superproduct_id":66}},{"product":{"category_id":null,"company_id":19,"description":"Empanada de Fusi\u00c3\u00b3n de 4 quesos ","id":148,"name":"Fusion de 4 quesos","orders":0,"pictures":[],"price":4.5,"running":null,"superproduct_id":66}},{"product":{"category_id":null,"company_id":19,"description":"Empanada de Pollo laqueado con miel y cerveza ","id":149,"name":"Pollo laqueado con miel y cerveza ","orders":0,"pictures":[],"price":4.5,"running":null,"superproduct_id":66}}],"tartin":[{"product":{"category_id":null,"company_id":19,"description":"Tartin de Puerros con parmesano ","id":150,"name":"Puerros con parmesano ","orders":0,"pictures":[],"price":4.5,"running":null,"superproduct_id":67}},{"product":{"category_id":null,"company_id":19,"description":"Tartin de Cebollas glaceadas con queso azul ","id":151,"name":"Cebollas glaceadas con queso azul ","orders":0,"pictures":[],"price":4.5,"running":null,"superproduct_id":67}},{"product":{"category_id":null,"company_id":19,"description":"Tartin de Esp\u00c3\u00a1rragos con gruyere ","id":152,"name":"Esparragos con gruyere ","orders":0,"pictures":[],"price":4.5,"running":null,"superproduct_id":67}},{"product":{"category_id":null,"company_id":19,"description":"Tartin de Jamon y queso ","id":153,"name":"Jamon y queso ","orders":0,"pictures":[],"price":4.5,"running":null,"superproduct_id":67}},{"product":{"category_id":null,"company_id":19,"description":"Tartin de Pascualina","id":154,"name":"Pascualina","orders":0,"pictures":[],"price":4.5,"running":null,"superproduct_id":67}},{"product":{"category_id":null,"company_id":19,"description":"Tartin de Zapallitos","id":155,"name":"Zapallitos","orders":0,"pictures":[],"price":4.5,"running":null,"superproduct_id":67}}],"pizzeta":[{"product":{"category_id":null,"company_id":19,"description":"Pizzeta de Muzzarella","id":156,"name":"Muzzarella","orders":0,"pictures":[],"price":4.5,"running":null,"superproduct_id":68}},{"product":{"category_id":null,"company_id":19,"description":"Pizzeta de Cuatro Quesos ","id":157,"name":"Cuatro Quesos","orders":0,"pictures":[],"price":4.5,"running":null,"superproduct_id":68}},{"product":{"category_id":null,"company_id":19,"description":"Pizzeta de Muzzarella y cebolla","id":158,"name":"Muzzarella y cebolla","orders":0,"pictures":[],"price":4.5,"running":null,"superproduct_id":68}}],"chipa":[{"product":{"category_id":null,"company_id":19,"description":"Chipa tradicional (pan de queso)","id":159,"name":"Chipa tradicional (pan de queso)","orders":0,"pictures":[],"price":4.5,"running":null,"superproduct_id":69}},{"product":{"category_id":null,"company_id":19,"description":"Chipa de Cebolla","id":160,"name":"Chipa de Cebolla","orders":0,"pictures":[],"price":4.5,"running":null,"superproduct_id":69}},{"product":{"category_id":null,"company_id":19,"description":"Chipa de Panceta ","id":161,"name":"Chipa de Panceta ","orders":0,"pictures":[],"price":4.5,"running":null,"superproduct_id":69}}],"gnoqui":[{"product":{"category_id":null,"company_id":19,"description":"Gnoquis de papa","id":162,"name":"Gnoquis de papa","orders":0,"pictures":[],"price":4.5,"running":null,"superproduct_id":70}}],"fideos":[{"product":{"category_id":null,"company_id":19,"description":"Fideos Tirabuzones","id":163,"name":"Tirabuzones","orders":0,"pictures":[],"price":4.5,"running":null,"superproduct_id":71}}],"ravioles":[{"product":{"category_id":null,"company_id":19,"description":"Ravioles de Ricota y Verdura ","id":164,"name":"Ricota y Verdura ","orders":0,"pictures":[],"price":4.5,"running":null,"superproduct_id":72}},{"product":{"category_id":null,"company_id":19,"description":"Ravioles de Ricota y Verdura ","id":165,"name":"Ricota y Verdura ","orders":0,"pictures":[],"price":4.5,"running":null,"superproduct_id":72}},{"product":{"category_id":null,"company_id":19,"description":"Ravioles de Verdura y Pollo","id":166,"name":"Verdura y Pollo","orders":0,"pictures":[],"price":4.5,"running":null,"superproduct_id":72}}],"raviolones":[{"product":{"category_id":null,"company_id":19,"description":"Raviolones Caprese","id":167,"name":"Caprese","orders":0,"pictures":[],"price":4.5,"running":null,"superproduct_id":73}},{"product":{"category_id":null,"company_id":19,"description":"Raviolones Jamon y Queso ","id":168,"name":"Jamon y Queso ","orders":0,"pictures":[],"price":4.5,"running":null,"superproduct_id":73}}],"pizza":[{"product":{"category_id":null,"company_id":19,"description":"Pizza de Muzzarella","id":169,"name":"Muzzarella","orders":0,"pictures":[],"price":4.5,"running":null,"superproduct_id":74}},{"product":{"category_id":null,"company_id":19,"description":"Pizza de Fugazzeta","id":170,"name":"Fugazzeta","orders":0,"pictures":[],"price":4.5,"running":null,"superproduct_id":74}},{"product":{"category_id":null,"company_id":19,"description":"Pizza de Jamon y Muzzarella ","id":171,"name":"Jamon y Muzzarella ","orders":0,"pictures":[],"price":4.5,"running":null,"superproduct_id":74}}],"helado":[{"product":{"category_id":null,"company_id":19,"description":"Helado de Banana Split ","id":172,"name":"Banana Split","orders":0,"pictures":[],"price":4.5,"running":null,"superproduct_id":75}},{"product":{"category_id":null,"company_id":19,"description":"Helado de Crema Americana ","id":173,"name":"Crema Americana ","orders":0,"pictures":[],"price":4.5,"running":null,"superproduct_id":75}},{"product":{"category_id":null,"company_id":19,"description":"Helado de Frutilla a la crema","id":174,"name":"Frutilla a la crema","orders":0,"pictures":[],"price":4.5,"running":null,"superproduct_id":75}},{"product":{"category_id":null,"company_id":19,"description":"Helado de Sambayon","id":175,"name":"Frutilla a la crema","orders":0,"pictures":[],"price":4.5,"running":null,"superproduct_id":75}},{"product":{"category_id":null,"company_id":19,"description":"Helado de Chocolate","id":176,"name":"Chocolate","orders":0,"pictures":[],"price":4.5,"running":null,"superproduct_id":75}},{"product":{"category_id":null,"company_id":19,"description":"Helado de Dulce de Leche Granizado ","id":177,"name":"Dulce de Leche Granizado ","orders":0,"pictures":[],"price":4.5,"running":null,"superproduct_id":75}},{"product":{"category_id":null,"company_id":19,"description":"Helado de Limon","id":178,"name":"Limon","orders":0,"pictures":[],"price":4.5,"running":null,"superproduct_id":75}},{"product":{"category_id":null,"company_id":19,"description":"Helado de Tramontana","id":179,"name":"Tramontana","orders":0,"pictures":[],"price":4.5,"running":null,"superproduct_id":75}},{"product":{"category_id":null,"company_id":19,"description":"Helado de Chocolate Italiano ","id":180,"name":"Chocolate Italiano ","orders":0,"pictures":[],"price":4.5,"running":null,"superproduct_id":75}},{"product":{"category_id":null,"company_id":19,"description":"Helado de Dulce de Leche El Noble ","id":181,"name":"Dulce de Leche El Noble ","orders":0,"pictures":[],"price":4.5,"running":null,"superproduct_id":75}},{"product":{"category_id":null,"company_id":19,"description":"Helado de Mascarpone con frutos del bosque","id":182,"name":"Mascarpone con frutos del bosque","orders":0,"pictures":[],"price":4.5,"running":null,"superproduct_id":75}}]}},{"store":[202,"Carne Suave","showstore",40.999999046325684,20,"Solo Empanadas","logo-soloempanadas.jpg",2.592034932549359,-34.6123,-58.4228],"products":{"empanada":[{"product":{"category_id":null,"company_id":20,"description":"Carne picada, cebolla, morron. Condimentos: comino, pimienta, pimentos y sal. ","id":183,"name":"Carne Suave","orders":0,"pictures":[],"price":4.1,"running":null,"superproduct_id":66}},{"product":{"category_id":null,"company_id":20,"description":"Carne picada, cebolla, morron. Condimientos: Comino, pimienta, pimenton,sal, aji molido.","id":184,"name":"Carne Picante","orders":0,"pictures":[],"price":4.1,"running":null,"superproduct_id":66}},{"product":{"category_id":null,"company_id":20,"description":"Choclo en grano, choclo cremoso y salsa blanca.","id":185,"name":"Humita","orders":0,"pictures":[],"price":4.1,"running":null,"superproduct_id":66}},{"product":{"category_id":null,"company_id":20,"description":"Jamon y Muzzarella","id":186,"name":"Jamon y Queso","orders":0,"pictures":[],"price":4.1,"running":null,"superproduct_id":66}},{"product":{"category_id":null,"company_id":20,"description":"Muzzarella, cebolla, pan rallado. Condimentos: Oregano, sal.","id":187,"name":"Queso y Cebolla","orders":0,"pictures":[],"price":4.1,"running":null,"superproduct_id":66}},{"product":{"category_id":null,"company_id":20,"description":"Muzarella, roquefort, pan rallado.","id":188,"name":"Roquefort","orders":0,"pictures":[],"price":4.1,"running":null,"superproduct_id":66}},{"product":{"category_id":null,"company_id":20,"description":"Acelga, salsa blanca.","id":189,"name":"Verdura","orders":0,"pictures":[],"price":4.1,"running":null,"superproduct_id":66}},{"product":{"category_id":null,"company_id":20,"description":"Pollo, cebolla, morron. Condimentos: Pimienta, pimenton y sal.","id":190,"name":"Pollo","orders":0,"pictures":[],"price":4.1,"running":null,"superproduct_id":66}},{"product":{"category_id":null,"company_id":20,"description":"Atun, cebolla, morron. Condimentos: Pimienta, pimenton, sal.","id":191,"name":"Atun","orders":0,"pictures":[],"price":4.1,"running":null,"superproduct_id":66}},{"product":{"category_id":null,"company_id":20,"description":"Dulce de leche","id":192,"name":"Dulce de leche","orders":0,"pictures":[],"price":4.1,"running":null,"superproduct_id":66}}]}}]
	 *             
	 *   }}
	 *                            
	 * 
	 * 
	 */

	

	@Override
	public SBNotification deserialize(JsonElement arg0, Type arg1,
			JsonDeserializationContext arg2)
			throws com.google.gson.JsonParseException {
		this.gson = new Gson();
		this.setType(arg0.getAsJsonObject().get("type").toString().replace("\"",""));
		this.setBody(arg0.getAsJsonObject().get("body").toString());

		Object object = null;
		
		try {
			Class<?> clazz;
			String fullClassName = "com.papitomarket.notifications.android." +  this.getType(); 
			clazz = Class.forName(fullClassName);
			Constructor<?> ctor = clazz.getConstructor(String.class,String.class);
			
			String[] args = { this.getBody(), this.getType() };
			object = ctor.newInstance((Object[])args);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (object==null){
			object = new SBNotification("{\"message\":\"Error getting smartbands notification.\"");
		}
		  return ((SBNotification)object);
	}
	
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	
	
}
