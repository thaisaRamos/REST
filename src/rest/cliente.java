package rest;


import java.awt.Desktop;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXB;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.xml.sax.InputSource;



@Path("generic")
public class cliente {
	
	StringWriter writer;
	
	public static void main(String[] args) throws MalformedURLException, IOException, JDOMException {
		cliente c = new cliente();
		c.Info("Brazil");
	}
	
	
@GET
@Produces("application/xml")
@Path("{name}")
public String Info(String pais) throws MalformedURLException, IOException, JDOMException {
		
		List<Artista> ArtistLista = new ArrayList<Artista>();
	    
		SAXBuilder sb2 = new SAXBuilder(); 
		
		SAXBuilder sb3 = new SAXBuilder();
		
		Document d2 = sb2.build(rankArtista(pais));
	    
		Document d3 = sb3.build(bandeira(pais));
	    
		Element rootNodeband = d3.getRootElement();
		
		
		Desktop desktop = Desktop.getDesktop();
		try {
			desktop.browse(new URI(rootNodeband.getValue()));
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
		System.out.println(rootNodeband.getValue());
		
		
		
		Element rootNodeband2 = rootNodeband.getChild("string");
		
		//System.out.println(rootNodeband2.getValue());
		
		
		Element rootNodeRank = d2.getRootElement();
		
		Element rootNodeRank2 = rootNodeRank.getChild("topartists");
		Element rootNodeRank3 = rootNodeRank2.getChild("artist");
		
		
		List listrank1 = rootNodeRank2.getChildren("artist");
		
		
		
	    
	    
	    	   
	
	System.out.println("----------------------------------------------------------");
	

	
	System.out.println("RANK DO ARTISTAS MAIS OUVIDOS");
	
	
	for (int i = 0; i < listrank1.size(); i++) {
		
			
		   Element node = (Element) listrank1.get(i);
		   Artista a = new Artista();
		   
		   int rank = i+1;
		   String nome = node.getChildText("name");
		  
		   
		//   System.out.println(node.getAttribute("rank").getValue());
		   System.out.println("Rank : " + (i+1) + "\nnome do artista " + node.getChildText("name") + "\nnumero ouvintes: " + node.getChildText("listeners") + "\n");
		  
		   a.setName(nome);
		   a.setRank(rank);
		  
		  Element nodeArt = node.getChild("artists");
		   
		 
		  ArtistLista.add(a);
	
	}
	System.out.println("Lista de Artistas:");
    for (Artista a : ArtistLista) {
        System.out.println(a.toString());
    }
	
    writer = new StringWriter();
    JAXB.marshal(ArtistLista, writer);
	
	
    return writer.toString();
	
}




public InputStream rankArtista(String Pais) throws MalformedURLException, IOException {

URL recurso = new URL ("http://ws.audioscrobbler.com/2.0/?method=geo.gettopartists&country=" + Pais + "&api_key=b25b959554ed76058ac220b7b2e0a026");

HttpURLConnection conexao = (HttpURLConnection) recurso.openConnection();
conexao.setDoOutput(true);



return conexao.getInputStream();
}

public InputStream bandeira(String Pais) throws MalformedURLException, IOException {


URL recurso = new URL ("http://10.9.98.241:8080/CountryFlagService/resources/countryflag/" + Pais);

HttpURLConnection conexao = (HttpURLConnection) recurso.openConnection();
conexao.setDoOutput(true);



return conexao.getInputStream();
}


}
