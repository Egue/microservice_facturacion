package com.hjsolutions.facturacion.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

import com.hjsolutions.facturacion.service.dto.InformatioInvoice;
import com.hjsolutions.facturacion.service.dto.InformatioInvoice.Items;
import com.hjsolutions.facturacion.service.dto.InformatioInvoice.Saldo;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import java.awt.Color;

import com.lowagie.text.pdf.Barcode128;
import com.lowagie.text.pdf.PdfCell;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfTable;
import com.lowagie.text.pdf.PdfWriter;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class GeneratePdfService {

    private final Color gris = new Color(220, 220, 220);
    private final Color verder = new Color(60, 179, 113);
    private final Font fontTableBold = FontFactory.getFont("HELVETICA", 8, Font.BOLD);
    private final Font fontTableNormal = FontFactory.getFont("HELVETICA", 8, Font.NORMAL);

    public void generatePdfInvoiceCablemag(InformatioInvoice informatioInvoice) {
        Document document = new Document();
        try {

            PdfWriter write = PdfWriter.getInstance(document, new FileOutputStream("Contrato.pdf"));
            document.open();
            URL imageUrl = getClass().getClassLoader().getResource("logo.png");
            if (imageUrl != null) {
                Image logo = Image.getInstance(imageUrl);
                logo.scaleToFit(450, 90);
                logo.setAlignment(Element.ALIGN_CENTER);
                document.add(logo);
                Paragraph name = header(informatioInvoice.getEnterprise().getName(), 6, Paragraph.ALIGN_CENTER);
                document.add(name);
                Paragraph address = header(informatioInvoice.getEnterprise().getAddress(), 6, Paragraph.ALIGN_CENTER);
                document.add(address);
                Paragraph url = header(informatioInvoice.getEnterprise().getUrl(), 6, Paragraph.ALIGN_CENTER);
                document.add(url);
                Paragraph resolution = header(informatioInvoice.getEnterprise().getResolutions(), 6,
                        Paragraph.ALIGN_CENTER);
                document.add(resolution);
                Paragraph cufe = header("CUFE: " + informatioInvoice.getInvoice().getCufe(), 9, Paragraph.ALIGN_LEFT);
                document.add(Chunk.NEWLINE);
                document.add(cufe);

                // TABLA
                PdfPTable tableHeader = tableHeader(informatioInvoice);
                document.add(tableHeader);
                // SaldosPendiente
                Paragraph salto = new Paragraph(" ");
                salto.setLeading(5f); // 🔹 altura del salto en puntos
                document.add(salto);
                PdfPTable tableSaldo = saldos(informatioInvoice.getPendiente(), "SALDOS PENDIENTES");
                document.add(tableSaldo);
                Paragraph salto2 = new Paragraph(" ");
                salto2.setLeading(2f); // 🔹 altura del salto en puntos
                document.add(salto2);
                PdfPTable tableCosumo = saldos(informatioInvoice.getConsumo(), "DESCRIPCIÓN CONSUMO MES");
                document.add(tableCosumo);
                Paragraph salto3 = new Paragraph(" ");
                salto3.setLeading(2f);
                document.add(salto3);
                // 
                PdfPTable tableInfo = createTableWithOne("INFORMACIÓN DE PLAN ADQUIRIDO",  1, informatioInvoice.getInvoice().getPlanAquirido(),100 , Color.YELLOW , Color.BLACK);
               //
               Float total = informatioInvoice.getPendiente().getTotal() + informatioInvoice.getConsumo().getTotal();

                PdfPTable tableTotal = createTableWithOne("TOTAL A PAGAR", 1, total.toString(), 100 , verder , verder);
                //contenedor
                PdfPTable contenedor = new PdfPTable(2);
                contenedor.setSpacingAfter(1f);
                contenedor.setSpacingBefore(2f);
                contenedor.setWidthPercentage(100);
                contenedor.setWidths(new float[]{1f,0.4f});
                PdfPCell cell1 = new PdfPCell(tableInfo);
                PdfPCell cell2 = new PdfPCell(tableTotal);
                cell1.setBorder(Rectangle.NO_BORDER);
                cell2.setBorder(Rectangle.NO_BORDER);
                contenedor.addCell(cell1);
                contenedor.addCell(cell2);
                document.add(contenedor);
                //table pagos
                PdfPTable tablePago = createTableWithFour("GRACIAS POR SU PAGO", "Fecha "+ informatioInvoice.getPago().getFecha(), "Punto de pago "+ informatioInvoice.getPago().getPunto(),"Monto cencelado" +informatioInvoice.getPago().getMonto());
                PdfPTable tableObservacion = createTableWithOne("OBSERVACIONES", 1, informatioInvoice.getObservacion(), 100, Color.WHITE, Color.BLACK);
                PdfPTable tablePqr = createTableWithFour("PQR's, ATENCIÓN AL CLIENTE","Telefax: " +informatioInvoice.getPqr().getTelefono(), "Dirección: "+informatioInvoice.getPqr().getDireccion(),"E-mail: " +informatioInvoice.getPqr().getEmail());
                //contenedro 2
                PdfPTable contenedor2 = new PdfPTable(2);
                contenedor2.setWidthPercentage(100);
                contenedor2.setWidths(new float[] {0.5f , 1f});
                PdfPCell ccell1 = new PdfPCell(tablePago); 
                PdfPCell ccell2 = new PdfPCell(tableObservacion);
                PdfPCell ccell3 = new PdfPCell(tablePqr);
                
                
                ccell1.setBorder(Rectangle.NO_BORDER);                
                ccell2.setBorder(Rectangle.NO_BORDER);
                ccell2.setRowspan(2);
                ccell3.setBorder(Rectangle.NO_BORDER); 

                contenedor2.addCell(ccell1);
                contenedor2.addCell(ccell2);
                contenedor2.addCell(ccell3); 

                document.add(contenedor2);
                //spacing
                Paragraph salto4 = new Paragraph(" ");
                salto4.setLeading(2f);
                document.add(salto4);
                //imagen table
                PdfPTable tableBarcode = new PdfPTable(1);
                tableBarcode.setWidthPercentage(100);
                tableBarcode.setWidths(new float[] {1f});
                Barcode128 barcode128 = createBarcode(informatioInvoice.getSerial().getCodigo());

                Image barcodeImage = barcode128.createImageWithBarcode(write.getDirectContent(), null, null);
                PdfPCell cellcode = new PdfPCell(barcodeImage);
                cellcode.setHorizontalAlignment(Element.ALIGN_CENTER);
                cellcode.setBorderWidthBottom(0);
                PdfPCell cellcodeMostrat = new PdfPCell(new Paragraph(informatioInvoice.getSerial().getMostrar(), fontTableNormal));
                cellcodeMostrat.setHorizontalAlignment(Element.ALIGN_CENTER);
                cellcodeMostrat.setBorderWidthTop(0);
                tableBarcode.addCell(cellcode);
                tableBarcode.addCell(cellcodeMostrat);
                document.add(tableBarcode);

                document.add(Chunk.NEWLINE);
                Paragraph pie = header(informatioInvoice.getPiePagina(), 8, Paragraph.ALIGN_CENTER);
                document.add(pie);
            } else {
                System.out.println("⚠️ No se encontró el logo en resources.");
            }

        } catch (DocumentException | IOException e) {
            System.out.println(e.getMessage());
            // TODO: handle exception
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }

        document.close();
    }

    private Barcode128 createBarcode(String cadena){

        Barcode128 barcode128 = new Barcode128();
        barcode128.setCode(cadena);
        barcode128.setCodeType(Barcode128.CODE128);
        barcode128.setFont(null);
        barcode128.setBarHeight(40f);
        barcode128.setX(1.5f);

        return barcode128;

    }

    private Paragraph header(String string, Integer fontSize, Integer align) {

        Font normal = FontFactory.getFont(FontFactory.HELVETICA, fontSize);
        Paragraph paragraph = new Paragraph(string, normal);
        paragraph.setAlignment(align);
        return paragraph;
    }

    private PdfPTable createTableWithOne(String title , Integer width , String content , Integer widthTable , Color fondoColor , Color borderColor){
        PdfPTable table = new PdfPTable(width);
        table.setWidthPercentage(widthTable); 
        table.addCell(createCell(title, PdfPCell.ALIGN_CENTER, Element.ALIGN_CENTER, fontTableBold, 1, 1, 1, 1, fondoColor, borderColor));
        table.addCell(createCell(content, PdfPCell.ALIGN_CENTER, Element.ALIGN_CENTER, fontTableNormal, 1, 1, 0, 1, Color.WHITE, borderColor));
        return table;
    }

    private PdfPTable createTableWithFour(String one , String two , String three, String four){
        PdfPTable table = new PdfPTable(1);
        table.setWidthPercentage(100);
        table.addCell(createCell(one, PdfPCell.ALIGN_LEFT, Element.ALIGN_LEFT, fontTableBold, 1, 1, 1, 0, gris, Color.BLACK));
        table.addCell(createCell(two, PdfPCell.ALIGN_LEFT, Element.ALIGN_LEFT, fontTableNormal, 1, 1, 0, 0, Color.WHITE, Color.BLACK));
        table.addCell(createCell(three, PdfPCell.ALIGN_LEFT, Element.ALIGN_LEFT, fontTableNormal, 1, 1, 0, 0, gris, Color.BLACK));
        table.addCell(createCell(four, PdfPCell.ALIGN_LEFT, Element.ALIGN_LEFT, fontTableNormal, 1, 1, 0, 1, Color.WHITE, Color.BLACK));

        return table;

    }

    private PdfPTable saldos(Saldo saldo, String title) {
        PdfPTable table = new PdfPTable(4);
        float[] columnsWidt = { 0.6f, 0.1f, 0.3f, 0.3f };
        table.setWidthPercentage(100);
        table.setWidths(columnsWidt);

        table.addCell(celdaUnida(title, PdfCell.ALIGN_CENTER, Element.ALIGN_CENTER, fontTableBold, 1, 1, 1, 0, verder,
                verder, 4));

        table.addCell(createCell("Concepto", PdfPCell.ALIGN_CENTER, Element.ALIGN_CENTER, fontTableNormal, 1, 0, 0, 1,
                Color.WHITE, verder));
        table.addCell(createCell("Cant", PdfPCell.ALIGN_CENTER, Element.ALIGN_CENTER, fontTableNormal, 0, 0, 0, 1,
                Color.WHITE, verder));
        table.addCell(createCell("V/rUnit", PdfPCell.ALIGN_CENTER, Element.ALIGN_CENTER, fontTableNormal, 0, 0, 0, 1,
                Color.WHITE, verder));
        table.addCell(createCell("Sub Total", PdfPCell.ALIGN_CENTER, Element.ALIGN_CENTER, fontTableNormal, 0, 1, 0, 1,
                Color.WHITE, verder));
        for (Items item : saldo.getSaldo()) {
            table.addCell(createCell(item.getConcepto(), PdfPCell.ALIGN_LEFT, Element.ALIGN_LEFT, fontTableNormal, 1, 0,
                    0, 0, Color.WHITE, verder));
            table.addCell(createCell(item.getCantidad(), PdfPCell.ALIGN_LEFT, Element.ALIGN_CENTER, fontTableNormal, 0,
                    0, 0, 0, Color.WHITE, verder));
            table.addCell(createCell(item.getvUnitario(), PdfPCell.ALIGN_LEFT, Element.ALIGN_CENTER, fontTableNormal, 0,
                    0, 0, 0, Color.WHITE, verder));
            table.addCell(createCell(item.getSubTotal(), PdfPCell.ALIGN_LEFT, Element.ALIGN_CENTER, fontTableNormal, 0,
                    1, 0, 0, Color.WHITE, verder));

        }
        for (int i = saldo.getSaldo().size(); i <= 8; i++) {
            table.addCell(createCell("", PdfPCell.ALIGN_LEFT, Element.ALIGN_LEFT, fontTableNormal, 1, 0, 0, 0,
                    Color.WHITE, verder));
            table.addCell(createCell("", PdfPCell.ALIGN_LEFT, Element.ALIGN_CENTER, fontTableNormal, 0, 0, 0, 0,
                    Color.WHITE, verder));
            table.addCell(createCell("", PdfPCell.ALIGN_LEFT, Element.ALIGN_CENTER, fontTableNormal, 0, 0, 0, 0,
                    Color.WHITE, verder));
            table.addCell(createCell("", PdfPCell.ALIGN_LEFT, Element.ALIGN_CENTER, fontTableNormal, 0, 1, 0, 0,
                    Color.WHITE, verder));

        }
        table.addCell(createCell("", 0, 0, fontTableBold, 1, 0, 0, 0, Color.WHITE, verder));
        table.addCell(createCell("", 0, 0, fontTableBold, 0, 0, 0, 0, Color.WHITE, verder));
        table.addCell(
                createCell("Total base", 0, Element.ALIGN_CENTER, fontTableBold, 0, 0, 0, 0, Color.WHITE, verder));
        table.addCell(createCell(((String) saldo.getBase().toString()), 0, Element.ALIGN_CENTER, fontTableBold, 0, 1, 0,
                0, Color.WHITE, verder));

        table.addCell(createCell("", 0, 0, fontTableBold, 1, 0, 0, 1, Color.WHITE, verder));
        table.addCell(createCell("", 0, 0, fontTableBold, 0, 0, 0, 1, Color.WHITE, verder));
        table.addCell(
                createCell("Total Impuesto", 0, Element.ALIGN_CENTER, fontTableBold, 0, 0, 0, 1, Color.WHITE, verder));
        table.addCell(createCell(((String) saldo.getImpuesto().toString()), 0, Element.ALIGN_CENTER, fontTableBold, 0,
                1, 0, 1, Color.WHITE, verder));

        return table;

    }

    private PdfPCell celdaUnida(String content, float borderWidth, int aling, Font font, int blf, int brg, int btp,
            int bbt, Color color,
            Color borderColor, Integer colspan) {
        PdfPCell cell = new PdfPCell(new Phrase(content, font));

        cell.setHorizontalAlignment(aling);
        cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
        cell.setPaddingTop(3f);
        cell.setPaddingBottom(3f);
        cell.setBackgroundColor(color);
        cell.setBorderColor(borderColor);
        // border
        cell.setBorderWidthLeft(blf);
        cell.setBorderWidthRight(brg);
        cell.setBorderWidthTop(btp);
        cell.setBorderWidthBottom(bbt);
        cell.setColspan(colspan);
        return cell;
    }

    private PdfPTable tableHeader(InformatioInvoice invoice) {
        PdfPTable table = new PdfPTable(4); //
        float[] columnsWidt = { 0.2f, 0.6f, 0.2f, 0.2f };
        table.setWidthPercentage(100);
        // table.getDefaultCell().setBorder(Rectangle.NO_BORDER);
        // table.getDefaultCell().setBorder(1);
        // table.getDefaultCell().setBorderColor(Color.GREEN);
        table.setWidths(columnsWidt);

        table.addCell(
                createCell(" ", PdfPCell.ALIGN_LEFT, Element.ALIGN_CENTER, fontTableBold, 1, 0, 1, 0, gris, verder));
        table.addCell(createCell("Datos del cliente", PdfPCell.ALIGN_LEFT, Element.ALIGN_CENTER, fontTableBold, 0, 0, 1,
                0, gris, verder));
        table.addCell(createCell("Factura No.", PdfPCell.ALIGN_LEFT, Element.ALIGN_LEFT, fontTableBold, 0, 0, 1, 0,
                gris, verder));
        table.addCell(createCell(invoice.getInvoice().getPrefijo() + " " + invoice.getInvoice().getConsecutivo(),
                PdfPCell.ALIGN_RIGHT, Element.ALIGN_RIGHT, fontTableBold, 0, 1, 1, 0, gris, verder));

        table.addCell(createCell("Referencia", PdfPCell.ALIGN_LEFT, Element.ALIGN_LEFT, fontTableBold, 1, 0, 0, 0,
                Color.WHITE, verder));
        table.addCell(createCell(invoice.getClient().getReferencia(), PdfPCell.ALIGN_LEFT, Element.ALIGN_LEFT,
                fontTableNormal, 0, 0, 0, 0, Color.WHITE, verder));
        table.addCell(createCell("Emitida", PdfPCell.ALIGN_LEFT, Element.ALIGN_LEFT, fontTableBold, 0, 0, 0, 0,
                Color.WHITE, verder));
        table.addCell(createCell(invoice.getInvoice().getFechaEmision(), PdfCell.ALIGN_CENTER, Element.ALIGN_RIGHT,
                fontTableNormal, 0, 1, 0, 0, Color.WHITE, verder));

        table.addCell(
                createCell("Nombre", PdfPCell.ALIGN_LEFT, Element.ALIGN_LEFT, fontTableBold, 1, 0, 0, 0, gris, verder));
        table.addCell(createCell(invoice.getClient().getNombre(), PdfPCell.ALIGN_LEFT, Element.ALIGN_LEFT,
                fontTableNormal, 0, 0, 0, 0, gris, verder));
        table.addCell(createCell("Pago oportuno hasta", PdfPCell.ALIGN_LEFT, Element.ALIGN_LEFT, fontTableBold, 0, 0, 0,
                0, gris, verder));
        table.addCell(createCell(invoice.getInvoice().getPagoOportuno(), PdfCell.ALIGN_CENTER, Element.ALIGN_RIGHT,
                fontTableNormal, 0, 1, 0, 0, gris, verder));

        table.addCell(createCell("CC. ó NIT", PdfPCell.ALIGN_LEFT, Element.ALIGN_LEFT, fontTableBold, 1, 0, 0, 0,
                Color.WHITE, verder));
        table.addCell(createCell(invoice.getClient().getDocument(), PdfPCell.ALIGN_LEFT, Element.ALIGN_LEFT,
                fontTableNormal, 0, 0, 0, 0, Color.WHITE, verder));
        table.addCell(createCell("Mes a pagar", PdfPCell.ALIGN_LEFT, Element.ALIGN_LEFT, fontTableBold, 0, 0, 0, 0,
                Color.WHITE, verder));
        table.addCell(createCell(invoice.getInvoice().getMesPagar(), PdfCell.ALIGN_CENTER, Element.ALIGN_RIGHT,
                fontTableNormal, 0, 1, 0, 0, Color.WHITE, verder));

        table.addCell(createCell("Dir Instalación", PdfPCell.ALIGN_LEFT, Element.ALIGN_LEFT, fontTableBold, 1, 0, 0, 0,
                gris, verder));
        table.addCell(createCell(invoice.getClient().getDirInstalacion(), PdfPCell.ALIGN_LEFT, Element.ALIGN_LEFT,
                fontTableNormal, 0, 0, 0, 0, gris, verder));
        table.addCell(createCell("Fecha de Suspensión", PdfPCell.ALIGN_LEFT, Element.ALIGN_LEFT, fontTableBold, 0, 0, 0,
                0, gris, verder));
        table.addCell(createCell(invoice.getInvoice().getFechaSuspensión(), PdfCell.ALIGN_CENTER, Element.ALIGN_RIGHT,
                fontTableNormal, 0, 1, 0, 0, gris, verder));

        table.addCell(createCell("Dir Correspondencia", PdfPCell.ALIGN_LEFT, Element.ALIGN_LEFT, fontTableBold, 1, 0, 0,
                1, Color.WHITE, verder));
        table.addCell(createCell(invoice.getClient().getDirCorrespondencia(), PdfPCell.ALIGN_LEFT, Element.ALIGN_LEFT,
                fontTableNormal, 0, 0, 0, 1, Color.WHITE, verder));
        table.addCell(createCell(" ", PdfPCell.ALIGN_LEFT, Element.ALIGN_LEFT, fontTableBold, 0, 0, 0, 1, Color.WHITE,
                verder));
        table.addCell(createCell(" ", PdfCell.ALIGN_CENTER, Element.ALIGN_RIGHT, fontTableNormal, 0, 1, 0, 1,
                Color.WHITE, verder));

        return table;

    }

    private PdfPCell createCell(String content, float borderWidth, int aling, Font font, int blf, int brg, int btp,
            int bbt, Color color,
            Color borderColor) {

        PdfPCell cell = new PdfPCell(new Phrase(content, font));

        cell.setHorizontalAlignment(aling);
        cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
        cell.setPaddingTop(3f);
        cell.setPaddingBottom(3f);
        cell.setBackgroundColor(color);
        cell.setBorderColor(borderColor);
        // border
        cell.setBorderWidthLeft(blf);
        cell.setBorderWidthRight(brg);
        cell.setBorderWidthTop(btp);
        cell.setBorderWidthBottom(bbt);

        return cell;

    }
}
