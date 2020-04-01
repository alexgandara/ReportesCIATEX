package imprimePDF;



import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.util.ByteArrayDataSource;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BarcodePDF417;
import com.itextpdf.text.pdf.BarcodeQRCode;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.BaseColor;



public class v21printPDF {


	//private static PdfWriter writer;




	public static final String FONT = ".\\resources\\fonts\\Consolas.ttf";
	public static final String FONT_AN = ".\\resources\\fonts\\arial-narrow.ttf";
	public static final String FONT_BOLD = ".\\resources\\fonts\\FrankfurtGothic-Bold.ttf";

	public static String $ORDEN_DE_COMPRA="";
	public static String $GUIA="";
	public static String $PEDIDO="";

	public static String $VENDEDOR="";
	public static String $TERMINOS="";
	public static String $FORMA_DE_PAGO="";

	public static String $PROVINCIA="";
	public static String $DISTRITO="";
	public static String $DEPARTAMENTO="";




	// private static String FILE = "c:/temp/FirstPdf.pdf";

	public static String[] Cabecera_Ticket  = new String[10];

	public static void imp_factura(String _file_xml, factura_cabecera Cabecera, factura_detalle[] Detalle, int _lineas_de_la_factura, String _file_pdf, String _file_jpg,  documentos_rel[] Rel) throws DocumentException, IOException {
		//String reportePDF = ".\\data\\20525719953\\05_pdfs\\xxx.pdf"; 



		for (int _x=0; _x<10;_x++) {


			String _id = Rel[_x].get_id();
			String _texto = Rel[_x].get_texto();
			try {

				_texto=_texto.replace("_", " ");



				System.out.println(_texto);

				if (_id.equals("1014")) {
					$FORMA_DE_PAGO=_texto;
				}


				if (_id.equals("1015")) {
					$ORDEN_DE_COMPRA=_texto;
				}


				if (_id.equals("1016")) {
					$GUIA=_texto;
				}

				if (_id.equals("1017")) {
					$PEDIDO=_texto;
				}

				if (_id.equals("1101")) {
					$VENDEDOR=_texto;
				}


				if (_id.equals("1102")) {
					$TERMINOS=_texto;
				}

				if (_id.equals("1103")) {
					$PROVINCIA=_texto;
				}

				if (_id.equals("1104")) {
					$DISTRITO=_texto;
				}

				if (_id.equals("1105")) {
					$DEPARTAMENTO=_texto;
				}





			} catch (Exception e) {

			}

		}

		Cabecera_Ticket[0]="";
		Cabecera_Ticket[1]="";
		Cabecera_Ticket[2]="";
		Cabecera_Ticket[3]="";
		Cabecera_Ticket[4]="";
		Cabecera_Ticket[5]="";


		String _archivo_ticket=Cabecera.get_serie()+"_cabecera_ticket.txt";




		try {
			readParam(_archivo_ticket);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String linea01 = Cabecera_Ticket[1];
		String linea02 = Cabecera_Ticket[2];
		String linea03 = Cabecera_Ticket[3];
		String linea04 = Cabecera_Ticket[4];
		String linea05 = Cabecera_Ticket[5];



		String reportePDF = _file_pdf;
		// 
		String formato_factura = _file_jpg; // .gif and .jpg are ok too!



		Document document = new Document();
		// step 2


		// Document document = new Document();
		File af = new File(reportePDF);


		af.delete();


		if (!af.exists()) { 




			PdfWriter.getInstance(document, new FileOutputStream(reportePDF));


			PdfWriter writer =
					PdfWriter.getInstance(document, new FileOutputStream(reportePDF));

			// step 3
			document.open();

			BaseFont bf = BaseFont.createFont(FONT,  BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			BaseFont bf_web = BaseFont.createFont(FONT_AN,  BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			BaseFont bf_bold = BaseFont.createFont(FONT_BOLD,  BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

			Font console = new Font(bf, 7);
			Font console_web = new Font(bf_web, 8);
			console_web.setColor(0, 0, 255);





			Image img = Image.getInstance(formato_factura);


			img.scalePercent(23);
			img.setAbsolutePosition(0, 70); // horizontal , vertical
			document.add(img);

			// step 4




			// ruc  emisor
			PdfContentByte canvas = writer.getDirectContent(); //  getDirectContentUnder();
			writer.setCompressionLevel(0);


			// calcula donde poner la razon social para uqe quede centrado
			int _tam_razon_social = Cabecera.get_razon_social_emisor().trim().length();
			System.out.println("tama�o:"+_tam_razon_social);
			String _razon_social = "";
			int _sobra = 0;

			if (_tam_razon_social>40) {
				_razon_social=Cabecera.get_razon_social_emisor().trim().substring(0, 40);
			} else {
				_sobra=(int)(40-_tam_razon_social)/2;
				System.out.println("sobre:"+_sobra);
				_sobra=_sobra*8;
				System.out.println("sobre:"+_sobra);

				_razon_social=Cabecera.get_razon_social_emisor().trim();	
			}


			// NOMBRE DEL DOCUMENTO
			canvas.saveState();                               // q
			canvas.beginText();                               // BT
			canvas.moveText(40+_sobra, 740);                         // 36 788 Td
			canvas.setFontAndSize(bf, 15); // /F1 12 Tf
			//	canvas.setColorFill(BaseColor.WHITE);
			//	 		canvas.showText(_razon_social);	        // (Hello World)Tj
			canvas.endText();                                 // ET
			canvas.restoreState();                            // Q


			// calcula donde poner la razon social para uqe quede centrado
			//		int _tam_direccion = Cabecera.get_direccion_emisor().trim().length();
			//		String _direccion = "";
			//		_sobra = 0;

			//		System.out.println(Cabecera.get_direccion_emisor()+" "+_tam_direccion);	

			//		if (_tam_direccion>46) {
			//			_direccion=Cabecera.get_direccion_emisor().trim().substring(0, 40);
			//		} else {
			//			_sobra=(int)(46-_tam_direccion)/2;
			//			_sobra=_sobra*7;

			//			_direccion=Cabecera.get_direccion_emisor().trim();	
			//		}



			// NOMBRE DEL DOCUMENTO
			canvas.saveState();                               // q
			canvas.beginText();                               // BT
			canvas.moveText(40+_sobra, 715);                         // 36 788 Td
			canvas.setFontAndSize(bf, 13); // /F1 12 Tf
			//	canvas.setColorFill(BaseColor.WHITE);
			//	 		canvas.showText(_direccion);	        // (Hello World)Tj
			canvas.endText();                                 // ET
			canvas.restoreState();                            // Q






			// Linea 01
			canvas.saveState();                               // q
			canvas.beginText();                               // BT
			canvas.moveText(38, 738);                         // 36 788 Td
			canvas.setFontAndSize(bf, 13); // /F1 12 Tf
			//canvas.setColorFill(BaseColor.WHITE);
			canvas.showText(linea01);	        // (Hello World)Tj
			canvas.endText();                                 // ET
			canvas.restoreState();                            // Q



			// Linea 02
			canvas.saveState();                               // q
			canvas.beginText();                               // BT
			canvas.moveText(45, 726);                         // 36 788 Td
			canvas.setFontAndSize(bf, 12); // /F1 12 Tf
			//	canvas.setColorFill(BaseColor.WHITE);
			canvas.showText(linea02);	        // (Hello World)Tj
			canvas.endText();                                 // ET
			canvas.restoreState();                            // Q


			// Linea 03
			canvas.saveState();                               // q
			canvas.beginText();                               // BT
			canvas.moveText(45, 714);                         // 36 788 Td
			canvas.setFontAndSize(bf, 12); // /F1 12 Tf
			//	canvas.setColorFill(BaseColor.WHITE);
			canvas.showText(linea03);	        // (Hello World)Tj
			canvas.endText();                                 // ET
			canvas.restoreState();                            // Q


			// Linea 04
			canvas.saveState();                               // q
			canvas.beginText();                               // BT
			canvas.moveText(45, 702);                         // 36 788 Td
			canvas.setFontAndSize(bf, 12); // /F1 12 Tf
			//	canvas.setColorFill(BaseColor.WHITE);
			canvas.showText(linea04);	        // (Hello World)Tj
			canvas.endText();                                 // ET
			canvas.restoreState();                            // Q



			// Linea 05
			canvas.saveState();                               // q
			canvas.beginText();                               // BT
			canvas.moveText(45, 690);                         // 36 788 Td
			canvas.setFontAndSize(bf, 12); // /F1 12 Tf
			//	canvas.setColorFill(BaseColor.WHITE);
			canvas.showText(linea05);	        // (Hello World)Tj
			canvas.endText();                                 // ET
			canvas.restoreState();                            // Q




			// NOMBRE DEL DOCUMENTO
			canvas.saveState();                               // q
			canvas.beginText();                               // BT
			canvas.moveText(405, 780);                         // 36 788 Td
			canvas.setFontAndSize(bf, 13); // /F1 12 Tf
			//	canvas.setColorFill(BaseColor.WHITE);
			canvas.showText(Cabecera.get_tipo_doc_descripcion());	        // (Hello World)Tj
			canvas.endText();                                 // ET
			canvas.restoreState();                            // Q




			canvas.saveState();                               // q
			canvas.beginText();                               // BT
			canvas.moveText(425, 760);                         // 36 788 Td
			//	canvas.setFontAndSize(BaseFont.createFont(), 11); // /F1 12 Tf
			canvas.setFontAndSize(bf, 11);
			canvas.showText("  RUC: "+Cabecera.get_ruc_emisor());	        // (Hello World)Tj
			canvas.endText();                                 // ET
			canvas.restoreState();                            // Q




			// serie
			canvas.saveState();                               // q
			canvas.beginText();                               // BT
			canvas.moveText(435, 735);                         // 36 788 Td
			canvas.setFontAndSize(bf, 11); // /F1 12 Tf
			canvas.showText(Cabecera.get_serie()+"-"+Cabecera.get_folio());	        // (Hello World)Tj
			canvas.endText();                                 // ET
			canvas.restoreState();                            // Q


			// RAZON SOCIAL  del receptor
			canvas.saveState();                               // q
			canvas.beginText();                               // BT
			canvas.moveText(40, 685);                         // 36 788 Td
			canvas.setFontAndSize(bf, 7); // /F1 12 Tf
			canvas.showText("CLIENTE      :"+ Formato.padRight(Cabecera.get_razon_social_receptor(),50));	        // (Hello World)Tj
			canvas.endText();                                 // ET
			canvas.restoreState();                            // Q










			if (Cabecera.get_direccion_receptor().length()>65) {
				int _tam = Cabecera.get_direccion_receptor().length();
				String _dir01 =  Cabecera.get_direccion_receptor().substring(0, 65);
				String _dir02 =  Cabecera.get_direccion_receptor().substring(65, _tam);

				// direccion social del emisor
				canvas.saveState();                               // q
				canvas.beginText();                               // BT
				canvas.moveText(40, 675);                         // 36 788 Td
				canvas.setFontAndSize(bf, 7); // /F1 12 Tf
				//	canvas.showText("CLIENTE       :"	        // (Hello World)Tj
				canvas.showText("DIRECCION    :"+_dir01);	        // (Hello World)Tj
				canvas.endText();                                 // ET
				canvas.restoreState();                            // Q


				// direccion social del emisor
				canvas.saveState();                               // q
				canvas.beginText();                               // BT
				canvas.moveText(40, 665);                         // 36 788 Td
				canvas.setFontAndSize(bf, 9); // /F1 12 Tf
				canvas.showText("        "+_dir02);	        // (Hello World)Tj
				canvas.endText();                                 // ET
				canvas.restoreState();                            // Q





			} else {
				// direccion social del emisor
				canvas.saveState();                               // q
				canvas.beginText();                               // BT
				canvas.moveText(40, 675);                         // 36 788 Td
				canvas.setFontAndSize(bf, 7); // /F1 12 Tf
				canvas.showText("DIRECCION    :"+Cabecera.get_direccion_receptor());	        // (Hello World)Tj
				canvas.endText();                                 // ET
				canvas.restoreState();                            // Q



			}






			// ruc del receptor
			canvas.saveState();                               // q
			canvas.beginText();                               // BT
			canvas.moveText(40, 655);                         // 36 788 Td
			canvas.setFontAndSize(bf, 7); // /F1 12 Tf
			//	canvas.showText("CLIENTE    :"	        // (Hello World)Tj
			if (Cabecera.get_tipo_doc().equals("01")) {
				canvas.showText("RUC          :"+Cabecera.get_ruc_receptor());	        // (Hello World)Tj

			} else {
				canvas.showText("RUC/DNI      :"+Cabecera.get_ruc_receptor());	        // (Hello World)Tj

			}

			canvas.endText();                                 // ET
			canvas.restoreState();                            // Q



			// ruc del receptor
			canvas.saveState();                               // q
			canvas.beginText();                               // BT
			canvas.moveText(40, 645);                         // 36 788 Td
			canvas.setFontAndSize(bf, 7); // /F1 12 Tf
			//	canvas.showText("CLIENTE    :"	        // (Hello World)Tj
			canvas.showText("O DE COMPRA  :"+$ORDEN_DE_COMPRA);	        // (Hello World)Tj
			canvas.endText();                                 // ET
			canvas.restoreState();                            // Q



			// ruc del receptor
			canvas.saveState();                               // q
			canvas.beginText();                               // BT
			canvas.moveText(40, 635);                         // 36 788 Td
			canvas.setFontAndSize(bf, 7); // /F1 12 Tf
			//	canvas.showText("CLIENTE    :"	        // (Hello World)Tj
			canvas.showText("GUIA         :"+$GUIA);	        // (Hello World)Tj
			canvas.endText();                                 // ET
			canvas.restoreState();                            // Q


			// ruc del receptor
			canvas.saveState();                               // q
			canvas.beginText();                               // BT
			canvas.moveText(40, 625);                         // 36 788 Td
			canvas.setFontAndSize(bf, 7); // /F1 12 Tf
			//	canvas.showText("CLIENTE    :"	        // (Hello World)Tj
			canvas.showText("VENDEDOR     :"+$VENDEDOR);	        // (Hello World)Tj
			canvas.endText();                                 // ET
			canvas.restoreState();                            // Q



			// distrito
			canvas.saveState();                               // q
			canvas.beginText();                               // BT
			canvas.moveText(220, 655);                         // 36 788 Td
			canvas.setFontAndSize(bf, 7); // /F1 12 Tf
			//	canvas.showText("CLIENTE      :"	        // (Hello World)Tj
			canvas.showText($PROVINCIA);	        // (Hello World)Tj
			canvas.endText();                                 // ET
			canvas.restoreState();                            // Q


			// DISTRUTO
			canvas.saveState();                               // q
			canvas.beginText();                               // BT
			canvas.moveText(220, 645);                         // 36 788 Td
			canvas.setFontAndSize(bf, 7); // /F1 12 Tf
			//	canvas.showText("CLIENTE      :"	        // (Hello World)Tj
			canvas.showText($DISTRITO);	        // (Hello World)Tj
			canvas.endText();                                 // ET
			canvas.restoreState();                            // Q



			// DEPARTEMENTO
			canvas.saveState();                               // q
			canvas.beginText();                               // BT
			canvas.moveText(220, 635);                         // 36 788 Td
			canvas.setFontAndSize(bf, 7); // /F1 12 Tf
			//	canvas.showText("CLIENTE      :"	        // (Hello World)Tj
			canvas.showText($DEPARTAMENTO);	        // (Hello World)Tj
			canvas.endText();                                 // ET
			canvas.restoreState();                            // Q





			// fecha de emision del docto
			canvas.saveState();                               // q
			canvas.beginText();                               // BT
			canvas.moveText(400, 685);                         // 36 788 Td
			canvas.setFontAndSize(bf, 7); // /F1 12 Tf
			// "GUIA       :"
			canvas.showText("F. EMISION     :"+Cabecera.get_fecha());	        // (Hello World)Tj
			canvas.endText();                                 // ET
			canvas.restoreState();                            // Q



			// fecha de emision del docto
			canvas.saveState();                               // q
			canvas.beginText();                               // BT
			canvas.moveText(400, 675);                         // 36 788 Td
			canvas.setFontAndSize(bf, 7); // /F1 12 Tf
			// "GUIA           :"
			canvas.showText("CONDICION      :"+$TERMINOS);	        // (Hello World)Tj
			canvas.endText();                                 // ET
			canvas.restoreState();                            // Q




			// fecha de emision del docto
			canvas.saveState();                               // q
			canvas.beginText();                               // BT
			canvas.moveText(400, 665);                         // 36 788 Td
			canvas.setFontAndSize(bf, 7); // /F1 12 Tf
			// "GUIA           :"
			canvas.showText("F. VENCIMIENTO :"+Cabecera.get_fecha_vencimiento());	        // (Hello World)Tj
			canvas.endText();                                 // ET
			canvas.restoreState();                            // Q


			// FORMA DE PAGO
			canvas.saveState();                               // q
			canvas.beginText();                               // BT
			canvas.moveText(400, 655);                         // 36 788 Td
			canvas.setFontAndSize(bf, 7); // /F1 12 Tf
			// "GUIA           :"
			canvas.showText("MEDIO DE PAGO  :"+$FORMA_DE_PAGO);	        // (Hello World)Tj
			canvas.endText();                                 // ET
			canvas.restoreState();                            // Q


			// pedido
			canvas.saveState();                               // q
			canvas.beginText();                               // BT
			canvas.moveText(400, 645);                         // 36 788 Td
			canvas.setFontAndSize(bf, 7); // /F1 12 Tf
			// "GUIA           :"
			canvas.showText("N. DE PEDIDO   :"+$PEDIDO);	        // (Hello World)Tj
			canvas.endText();                                 // ET
			canvas.restoreState();                            // Q


			//moneda
			canvas.saveState();                               // q
			canvas.beginText();                               // BT
			canvas.moveText(400, 635);                         // 36 788 Td
			canvas.setFontAndSize(bf, 7); // /F1 12 Tf
			//canvas.showText("Moneda:           "+Cabecera.get_moneda());	        // (Hello World)Tj
			if (Cabecera.get_moneda().equals("PEN")) {
				canvas.showText("MONEDA         :"+"SOLES");	        // (Hello World)Tj
			} else {
				canvas.showText("MONEDA         :"+"DOLARES");	        // (Hello World)Tj
			}

			canvas.endText();                                 // ET
			canvas.restoreState();                            // Q







			//		String _fecha = Cabecera.get_fecha_vencimiento();
			//		String _fecha_final = _fecha;

			//fecha vencimiento
			//		canvas.saveState();                               // q
			//		canvas.beginText();                               // BT
			//		canvas.moveText(500, 641);                         // 36 788 Td
			//		canvas.setFontAndSize(bf, 7); // /F1 12 Tf
			//		if (!Cabecera.get_fecha().equals(_fecha_final)) {
			//			canvas.showText(_fecha_final);
			//		}
			//		canvas.endText();                                 // ET
			//		canvas.restoreState();                            // Q









			// descuento cabecera
			//		canvas.saveState();                               // q
			//		canvas.beginText();                               // BT
			//		canvas.moveText(495, 620);                         // 36 788 Td
			//		canvas.setFontAndSize(bf, 9); // /F1 12 Tf
			//		canvas.showText(Formato.dinero(Cabecera.get_descuentos_cabecera()));	        // (Hello World)Tj
			//		canvas.endText();                                 // ET
			//		canvas.restoreState();                            // Q





			// descuento detalle
			//		canvas.saveState();                               // q
			//		canvas.beginText();                               // BT
			//		canvas.moveText(495, 608);                         // 36 788 Td
			//		canvas.setFontAndSize(bf, 9); // /F1 12 Tf
			//		canvas.showText(Formato.dinero(Cabecera.get_descuentos_detalle()));	        // (Hello World)Tj
			//		canvas.endText();                                 // ET
			//		canvas.restoreState();                            // Q








			// TOTAL subtotal
			canvas.saveState();                               // q
			canvas.beginText();                               // BT
			canvas.moveText(362, 272);                         // 36 788 Td
			canvas.setFontAndSize(bf, 9); // /F1 12 Tf
			//			              //"Sub Total   :"
			if (Cabecera.get_tipo_doc().equals("01")) {
				if (Cabecera.get_moneda().equals("PEN")) {
					canvas.showText("Total Ventas Gravadas   S/:"+Formato.dinero(Cabecera.get_total_gravado()+Cabecera.get_total_descuentos()));
				} else {
					canvas.showText("Total Ventas Gravadas    $:"+Formato.dinero(Cabecera.get_total_gravado()+Cabecera.get_total_descuentos()));
				}
			} else {


				if (Cabecera.get_moneda().equals("PEN")) {
					//			canvas.showText("Total Ventas Gravadas   S/:"+Formato.dinero(Cabecera.get_total_gravado()+Cabecera.get_total_descuentos()+Cabecera.get_total_igv()));
				} else {
					//			canvas.showText("Total Ventas Gravadas    $:"+Formato.dinero(Cabecera.get_total_gravado()+Cabecera.get_total_descuentos()+Cabecera.get_total_igv()));
				}

			}
			//			canvas.showText("Sub Total S/:"+Formato.dinero(Cabecera.get_total_gravado()+Cabecera.get_total_inafecto()+Cabecera.get_total_exonerado()+Cabecera.get_total_descuentos()));
			canvas.endText();                                 // ET
			canvas.restoreState();                            // Q



			// TOTAL inafecto
			canvas.saveState();                               // q
			canvas.beginText();                               // BT
			canvas.moveText(362, 261);                         // 36 788 Td
			canvas.setFontAndSize(bf, 9); // /F1 12 Tf
			//	"Sub Total   :"
			if (Cabecera.get_tipo_doc().equals("01")) {
				if (Cabecera.get_moneda().equals("PEN")) {
					//              "Total Ventas Gravadas    $:"
					canvas.showText("Total Ventas Inafectas  S/:"+Formato.dinero(Cabecera.get_total_inafecto()));
				} else {
					canvas.showText("Total Ventas Inafectas   $:"+Formato.dinero(Cabecera.get_total_inafecto()));
				}
			} else {


			}

			canvas.endText();                                 // ET
			canvas.restoreState();                            // Q



			// TOTAL exonerado
			canvas.saveState();                               // q
			canvas.beginText();                               // BT
			canvas.moveText(362, 250);                         // 36 788 Td
			canvas.setFontAndSize(bf, 9); // /F1 12 Tf
			//	"Sub Total   :"

			if (Cabecera.get_tipo_doc().equals("01")) {
				if (Cabecera.get_moneda().equals("PEN")) {
					//   "Descuento S/:"
					//         "Total Ventas Gravadas    $:"
					canvas.showText("Total Ventas Exoneradas S/:"+Formato.dinero(Cabecera.get_total_exonerado()));
				} else {
					canvas.showText("Total Ventas Exoneradas  $:"+Formato.dinero(Cabecera.get_total_exonerado()));
				}
			}

			canvas.endText();                                 // ET
			canvas.restoreState();                            // Q





			// TOTAL descuentos
			canvas.saveState();                               // q
			canvas.beginText();                               // BT
			canvas.moveText(362, 239);                         // 36 788 Td
			canvas.setFontAndSize(bf, 9); // /F1 12 Tf
			//	"Sub Total   :"
			if (Cabecera.get_moneda().equals("PEN")) {
				//   "Descuento S/:"
				//            "Total Ventas Gravadas    $:"
				canvas.showText("Descuento Total     "+Formato.dinero3a(Cabecera.get_porciento_descuento()*100)+ "% S/:"+Formato.dinero(Cabecera.get_total_descuentos()));
			} else {
				canvas.showText("Descuento Total     "+Formato.dinero3a(Cabecera.get_porciento_descuento()*100)+ "%  $:"+Formato.dinero(Cabecera.get_total_descuentos()));
			}


			canvas.endText();                                 // ET
			canvas.restoreState();                            // Q







			// TOTAL anticipo
			canvas.saveState();                               // q
			canvas.beginText();                               // BT
			canvas.moveText(429, 241);                         // 36 788 Td
			canvas.setFontAndSize(bf, 9); // /F1 12 Tf
			//	"Sub Total   :"
			if (Cabecera.get_tipo_doc().equals("01")) {
				if (Cabecera.get_moneda().equals("PEN")) {
					//			canvas.showText("V. Venta  S/:"+Formato.dinero(Cabecera.get_total_gravado()+Cabecera.get_total_inafecto()+Cabecera.get_total_exonerado()));
				} else {
					//			canvas.showText("V. Venta   $:"+Formato.dinero(Cabecera.get_total_gravado()+Cabecera.get_total_inafecto()+Cabecera.get_total_exonerado()));
				}
			}

			canvas.endText();                                 // ET
			canvas.restoreState();                            // Q  




			// TOTAL igv
			canvas.saveState();                               // q
			canvas.beginText();                               // BT
			canvas.moveText(362, 228);                         // 36 788 Td
			canvas.setFontAndSize(bf, 9); // /F1 12 Tf
			//	"Sub Total   :"
			if (Cabecera.get_tipo_doc().equals("01")) {
				//       	         "IGV      18%:"
				if (Cabecera.get_moneda().equals("PEN")) { 
					canvas.showText("Total IGV           18% S/:"+Formato.dinero(Cabecera.get_total_igv()));
				} else {
					canvas.showText("Total IGV           18%  $:"+Formato.dinero(Cabecera.get_total_igv()));

				}
			} else {

			}
			canvas.endText();                                 // ET
			canvas.restoreState();                            // Q  





			// TOTAL DE LA FACTURAS
			canvas.saveState();                               // q
			canvas.beginText();                               // BT
			canvas.moveText(474, 208);                         // 36 788 Td
			canvas.setFontAndSize(bf, 10); // /F1 12 Tf
			//	canvas.setColorFill(BaseColor.WHITE);
			// canvas.showText("Total:      "+Cabecera.get_total());	
			//	"Sub Total   :"
			if (Cabecera.get_moneda().equals("PEN")) {
				canvas.showText("S/:"+Formato.dinero(Cabecera.get_total()));
			} else {
				canvas.showText(" $:"+Formato.dinero(Cabecera.get_total()));
			}


			canvas.endText();                                 // ET
			canvas.restoreState();                            // Q



			// descuento cabecera
			canvas.saveState();                               // q
			canvas.beginText();                               // BT
			canvas.moveText(377, 115);                         // 36 788 Td
			canvas.setFontAndSize(bf, 9); // /F1 12 Tf
			//	canvas.showText("Tipo de Operaci�n:"+Cabecera.get_tipo_operacion());	        // (Hello World)Tj
			canvas.endText();                                 // ET
			canvas.restoreState();                            // Q





			// para el facturador
			Cabecera.set_total_letra(denomina.main(Cabecera.get_total()));
			//		System.out.println("Importe con Letra _ _ _ _ _ _ : " + Cabecera.get_total_letra());


			
			// cantidad en letra
			canvas.saveState();                               // q
			canvas.beginText();                               // BT
			canvas.moveText(40, 315);                         // 36 788 Td
			canvas.setFontAndSize(bf, 8); // /F1 12 Tf
			if (Detalle[0].get_tipo_igv().equals("20")) {
				if (Cabecera.get_tipo_doc().equals("01")) {
					canvas.showText("Factura Exonerada de IGV");	        // (Hello World)Tj
				} else {
					canvas.showText("Boleta Exonerada de IGV");	        // (Hello World)Tj
				}

				
			}
			canvas.endText();                                 // ET
			canvas.restoreState();                            // Q



			// cantidad en letra
			canvas.saveState();                               // q
			canvas.beginText();                               // BT
			canvas.moveText(120, 297);                         // 36 788 Td
			canvas.setFontAndSize(bf, 8); // /F1 12 Tf
			if (Cabecera.get_moneda().equals("PEN")) {
				canvas.showText(Cabecera.get_total_letra()+" SOLES.");	        // (Hello World)Tj
			} else {
				canvas.showText(Cabecera.get_total_letra()+" DOLARES.");	        // (Hello World)Tj
			}
			canvas.endText();                                 // ET
			canvas.restoreState();                            // Q




			// resumen hash
			canvas.saveState();                               // q
			canvas.beginText();                               // BT
			canvas.moveText(132, 126);                         // 36 788 Td
			canvas.setFontAndSize(bf, 7); // /F1 12 Tf
			canvas.showText("Hash: "+Cabecera.get_codigo_hash());	        // (Hello World)Tj
			canvas.endText();                                 // ET
			canvas.restoreState();                            // Q

			//	  	    BarcodePDF417 pdf417 = new BarcodePDF417();
			//	  	    String text = Cabecera.get_sello();

			//	  	       pdf417.setText(text);
			//	  	       Image barras = pdf417.getImage(); 
			//	  	       barras.setAbsolutePosition(48, 160); 
			//	  	       barras.scalePercent(90, 120);
			//	  	       document.add(barras);

			//   ("Cantidad   Unidad        Descripci�n                                                                                 C�digo                   P.Unitario                      Importe      ");

			// resumen hash
			canvas.saveState();                               // q
			canvas.beginText();                               // BT
			canvas.moveText(30, 608);                         // 36 788 Td
			canvas.setFontAndSize(bf, 7); // /F1 12 Tf
			if (Cabecera.get_tipo_doc().equals("01")) {
				canvas.showText("    Cantidad   Unidad    Descripci�n                                                 C�digo          V.Unitario              Importe      ");	    	
			} else {
				canvas.showText("    Cantidad   Unidad    Descripci�n                                                 C�digo          P.Unitario              Importe      ");	    	        // (Hello World)Tj
			}


			canvas.endText(); 
			canvas.restoreState();                            // Q


			String _contenido_qr = Cabecera.get_ruc_emisor()+"|"+Cabecera.get_tipo_documento()+"|"+   
					Cabecera.get_serie()+"-"+Cabecera.get_folio()+"|"+
					Cabecera.get_total_igv()+"|"+Cabecera.get_total()+"|"+Cabecera.get_fecha_qr()+"|"+
					Cabecera.get_tipo_doc_adquiriente()+"|"+Cabecera.get_ruc_receptor()+"|"+Cabecera.get_codigo_hash();

			BarcodeQRCode barcodeQRCode = new BarcodeQRCode(_contenido_qr, 90, 90, null);
			Image codeQrImage = barcodeQRCode.getImage();
			codeQrImage.setAbsolutePosition(156, 142);
			document.add(codeQrImage);








			Paragraph _linea00 = new Paragraph(8);
			Chunk _espacio = new Chunk(" ");
			_linea00.add(_espacio);


			//special font sizes
			BaseFont bf_arial = BaseFont.createFont(FONT, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			Font arial = new Font(bf_arial, 6);




			for (int z = 1; z<=26; z++) {
				document.add(_linea00);
			}


			Paragraph _linea01 = new Paragraph(7);

			String _desc ="";







			int _linea_factura=0;




			//   _cabecera.setFont(console);


			for (int i=0; i<_lineas_de_la_factura; i++) {
				//	 System.out.println("esta en la linea "+i);
				//	System.out.println(Detalle[i].get_descripcion());
				if (Detalle[i].get_descripcion()!=null) { 	 
					if (!Detalle[i].get_descripcion().trim().equals("")) {


						if (Detalle[i].get_codigo()==null) {
							Detalle[i].set_codigo(".");
						}



						Chunk _producto = new Chunk(Formato.padRight(Detalle[i].get_codigo(),15));
						Chunk _descripcion = new Chunk(Formato.padRight(Formato.cadena59(Detalle[i].get_descripcion()),59));





						if (Detalle[i].get_unidad()==null) {
							Detalle[i].set_unidad("KG");
						}

						if (Detalle[i].get_unidad().equals("KGM")) {
							Detalle[i].set_unidad("KG");
						}

						if (Detalle[i].get_unidad().equals("MTR")) {
							Detalle[i].set_unidad("MTS");
						}

						if (Detalle[i].get_unidad().equals("NIU")) {
							Detalle[i].set_unidad("UNI");
						}

						if (Detalle[i].get_unidad().equals("BX")) {
							Detalle[i].set_unidad("CJ");
						}

						if (Detalle[i].get_unidad().equals("PF")) {
							Detalle[i].set_unidad("PAL");
						}

						Detalle[i].set_total_linea(Detalle[i].get_subtotal()); //-Detalle[i].get_descuento());



						Chunk _unidad_de_medida = new Chunk(Formato.cadena5(Detalle[i].get_unidad()));
						Chunk _cantidad = new Chunk(Formato.cant3(Detalle[i].get_cantidad()));
						Chunk _precio = new Chunk(Formato.dinero4(Detalle[i].get_precio_unitario()));
						Chunk _precio_con_igv = new Chunk(Formato.dinero4(round((Detalle[i].get_precio_unitario()*1.18),4)));
						Chunk _importe = new Chunk(Formato.dinero(Detalle[i].get_subtotal()));
						Chunk _igv = new Chunk(Formato.dinero(Detalle[i].get_igv()));
						Chunk _importe_sin_igv = new Chunk(Formato.dinero(Detalle[i].get_subtotal()));
						Chunk _descuento = new Chunk(Formato.dinero(Detalle[i].get_descuento()));
						//	 Chunk _linea = new Chunk(Formato.dinero2(Detalle[i].get_num_linea()));
						double _total_linea_num_con_igv=round((Detalle[i].get_precio_unitario())*Detalle[i].get_cantidad()*1.18,3);
						double _total_linea_num_sin_igv=round((Detalle[i].get_precio_unitario())*Detalle[i].get_cantidad(),3);

						Chunk Chunck_total_linea_con_igv = new Chunk(Formato.dineros3(_total_linea_num_con_igv));  
						
						Chunk Chunck_total_linea_sin_igv = new Chunk(Formato.dineros3(_total_linea_num_sin_igv));  

						_espacio.setFont(console);
						//  	_lineas_de_la_factura






						_producto.setFont(console);
						_descripcion.setFont(console);
						_cantidad.setFont(console);
						_unidad_de_medida.setFont(console);

						_precio.setFont(console);
						_precio_con_igv.setFont(console);
						_importe.setFont(console);
						_importe_sin_igv.setFont(console);

						_igv.setFont(console);
						_descuento.setFont(console);
						Chunck_total_linea_con_igv.setFont(console);

						Chunck_total_linea_sin_igv.setFont(console);







						// 	_linea01.add(_espacio);
						if (Detalle[i].get_codigo()!=".") {
							if (Detalle[i].get_codigo().equals(".")) {
								_linea01.add(_espacio);
								_linea01.add(_espacio);
								_linea01.add(_espacio);
								_linea01.add(_espacio);
								_linea01.add(_espacio);
								_linea01.add(_espacio);
								_linea01.add(_espacio);
								_linea01.add(_espacio);
								_linea01.add(_espacio);

							} else {

								_linea_factura++;

								Chunk _linea = new Chunk(Formato.dinero2(_linea_factura));
								_linea.setFont(console);
								//		 	  	    				 

								//	_linea01.add(_espacio);
								//	_linea01.add(_linea);
								_linea01.add(_cantidad);
								_linea01.add(_espacio);
								_linea01.add(_espacio);
								_linea01.add(_espacio);
								_linea01.add(_espacio);

								_linea01.add(_espacio);
								_linea01.add(_unidad_de_medida);
								_linea01.add(_espacio);
								_linea01.add(_espacio);
								_linea01.add(_espacio);

								//	_linea01.add(_espacio);
								//	_linea01.add(_producto);



							}
						} else {


							for (int _l=0; _l<21; _l++) {
								_linea01.add(_espacio);
							} 		 


						}


						//		System.out.println(_descripcion);

						_linea01.add(_espacio);
						_linea01.add(_descripcion);


						if (!Detalle[i].get_codigo().equals(".")) {

							//	 _linea01.add(_espacio);
							//	 _linea01.add(_espacio);


							//	_linea01.add(_espacio);
							_linea01.add(_producto);
							_linea01.add(_espacio);



							if (Cabecera.get_tipo_doc().equals("03")) {
								if (Detalle[i].get_tipo_igv().equals("10")) {
									_linea01.add(_precio_con_igv);	
								} else {
									_linea01.add(_precio);
								}
								
							} else {
								if (Detalle[i].get_tipo_igv().equals("10")) {
									_linea01.add(_precio);
								} else {
									_linea01.add(_precio);
								}
									
							}

							_linea01.add(_espacio);

							_linea01.add(_espacio);

							_linea01.add(_espacio);
							_linea01.add(_espacio);
							_linea01.add(_espacio);
							_linea01.add(_espacio);

							_linea01.add(_espacio);
							_linea01.add(_espacio);
							_linea01.add(_espacio);
							_linea01.add(_espacio);
							_linea01.add(_espacio);
							_linea01.add(_espacio);
							if (Cabecera.get_tipo_doc().equals("03")) {
								if (Detalle[i].get_tipo_igv().equals("10")) {
									_linea01.add(Chunck_total_linea_con_igv);	
								} else {
									_linea01.add(Chunck_total_linea_sin_igv);
								}
								

							} else {
								_linea01.add(Chunck_total_linea_sin_igv);
							}


							//	_linea01.add(_descuento);
							//	_linea01.add(_espacio);
							//	_linea01.add(_total_linea);









							/*		 if (Cabecera.get_tipo_doc_descripcion().equals("BOLETA ELECTRONICA")) {
		 	  	    				 _linea01.add(_precio_con_igv);
		 	  	    			 }

		 	  	    			 else
		 	  	    			 {
		 	  	    				 _linea01.add(_precio_con_igv);
		 	  	    			 }
		 	  	    			 _linea01.add(_espacio);

		 	  	    			 _linea01.add(_espacio);
		 	  	    			 _linea01.add(_espacio);

		 	  	    			 if (Cabecera.get_tipo_doc_descripcion().equals("BOLETA ELECTRONICA")) {
		 	  	    				 _linea01.add(_importe);
		 	  	    			 }

		 	  	    			 else
		 	  	    			 {
		 	  	    				 _linea01.add(_importe);
		 	  	    			 }
		 	  	    			 // _linea01.add(_importe_sin_igv);
							 */



						}
					} 
				}


				document.add(_linea01);
				_linea01.removeAll(_linea01);
			}



			// Q



			String _signo="";

			if (Cabecera.get_moneda().equals("PEN")) {
				_signo="S/";
			} else {
				_signo=" $";
			}


			// TOTAL subtotal
			canvas.saveState();                               // q
			canvas.beginText();                               // BT
			canvas.moveText(455, 172);                         // 36 788 Td
			canvas.setFontAndSize(bf, 8); // /F1 12 Tf

			if (Cabecera.get_tipo_doc().equals("01")) {
				//	canvas.showText("Subtotal:"+_signo+Formato.dinero(Cabecera.get_subtotal()));
			}

			canvas.endText();                                 // ET
			canvas.restoreState();                            // Q

			// TOTAL subtotal
			canvas.saveState();                               // q
			canvas.beginText();                               // BT
			canvas.moveText(455, 162);                         // 36 788 Td
			canvas.setFontAndSize(bf, 8); // /F1 12 Tf
			if (Cabecera.get_tipo_doc().equals("01")) {
				//  	canvas.showText("IGV 18% :"+_signo+Formato.dinero(Cabecera.get_total_igv()));
			}
			canvas.endText();                                 // ET
			canvas.restoreState();                            // Q  

			// TOTAL DE LA FACTURAS
			canvas.saveState();                               // q
			canvas.beginText();                               // BT
			canvas.moveText(455, 152);                         // 36 788 Td
			canvas.setFontAndSize(bf, 8); // /F1 12 Tf
			// canvas.showText("Total:      "+Cabecera.get_total());	        // (Hello World)Tj
			//    canvas.showText("Total:   "+_signo+Formato.dinero(Cabecera.get_total()));
			canvas.endText();                                 // ET
			canvas.restoreState();                            // Q









			// step 5
			document.close();		



		}


	}



	public static void readParam(String _file_parametros) throws Exception {

		InputStream is_param = new FileInputStream(_file_parametros);
		DataSource ds_param = (DataSource) new ByteArrayDataSource(is_param,"application/octet-stream");
		DataHandler dhandler_param = new DataHandler((javax.activation.DataSource) ds_param);

		Object content = dhandler_param.getContent();

		BufferedReader br = null;

		int x=1;
		try {

			String sCurrentLine;
			br = new BufferedReader(new InputStreamReader((InputStream) content));

			while ((sCurrentLine = br.readLine()) != null) {
				Cabecera_Ticket[x]=sCurrentLine;
				//	System.out.println(x+"  "+sCurrentLine);


				x++;

			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

	}



	public static double round(double value, int places) {
		if (places < 0) throw new IllegalArgumentException();

		long factor = (long) Math.pow(10, places);
		value = value * factor;
		long tmp = Math.round(value);
		return (double) tmp / factor;
	}



}
