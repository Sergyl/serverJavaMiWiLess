package com.miwiless.main;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;

public class Server {
 
	private static AudioFormat format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 8000, 16, 2, 4, 8000, false);
	private static DatagramSocket socketUDP;
	private static boolean running = false;
	private static SourceDataLine speakers;

	public static void main(String[] args) throws Exception {
 
        final int PUERTO = 5000;
        byte[] buffer = new byte[8000];
        DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, format);
        speakers = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
        speakers.open(format);
        speakers.start();
 
        try {
            System.out.println("Iniciado el servidor UDP...");
            socketUDP = new DatagramSocket(PUERTO);
            running = true; 
            
            //Siempre atendera peticiones
            while(running) {
           
                //Preparo la respuesta
                DatagramPacket peticion = new DatagramPacket(buffer, buffer.length);
                 
                //Recibo el datagrama
                socketUDP.receive(peticion);
                 
                //Convierto lo recibido y mostrar el mensaje
                //String mensaje = new String(peticion.getData());
                //System.out.println(mensaje);
                
               try {
            	   speakers.write(peticion.getData(), 0, peticion.getLength());
            	   
                //ByteArrayInputStream msgInput = new ByteArrayInputStream(peticion.getData());
                //AudioInputStream audioStream = AudioSystem.getAudioInputStream(msgInput);
                //System.out.println(audioStream.toString());
                //clip.open(audioStream);
                //clip.start();
                
                } catch (Exception e){
                	System.out.println(e.getMessage());
                }
 
                //buffer = mensaje.getBytes();
 
                //creo el datagrama
                //DatagramPacket respuesta = new DatagramPacket(buffer, buffer.length, direccion, puertoCliente);
 
                //Envio la información
                //System.out.println("Envio la informacion del cliente");
                //socketUDP.send(respuesta);
                 
            }
 
        } catch (SocketException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
 
}