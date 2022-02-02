package com.mx.forworkes.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Font;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import com.mx.forworkes.dto.puestoDto;
import com.mx.forworkes.entity.PuestoEntity;
import com.mx.forworkes.exception.ExceptionGlobal;
import com.mx.forworkes.exception.PuestoException;
import com.mx.forworkes.repository.IPuestoRepository;
import com.mx.forworkes.service.IpuestoService;

/**
 * @author IgnacioCisnerosJuare
 *
 */

@Service
public class puestoServiceImpl implements IpuestoService {

	private static final Logger LOGGER = LogManager.getLogger(puestoServiceImpl.class);

	@Autowired
	private IPuestoRepository puestoRepository;

	/**
	 * Metodo encargado de extraer el listado de los puestos existentes
	 * 
	 * @throws PuestoException
	 */
	@Override
	public List<puestoDto> extrerPuestos() throws PuestoException {

		List<puestoDto> listaPuestosDTO = new ArrayList<>();

		try {
			List<PuestoEntity> listaPuestos = puestoRepository.findByActivoTrue();

			if (listaPuestos.size() > 0) {
				LOGGER.info("::NUMERO DE PUESTOS ENCONTRADOS EN LA BASE DE DATOS: " + listaPuestos.size() + " ::");
				for (PuestoEntity elemento : listaPuestos) {

					puestoDto puesto = new puestoDto();
					puesto.setPuesto_id(elemento.getPuestoId());
					puesto.setDescripcion(elemento.getDescripcion());
					listaPuestosDTO.add(puesto);

				}
				return listaPuestosDTO;
			} else {
				LOGGER.info("::NO SE ENCONTRARON PUESTOS EN LA BASE DE DATOS::");
				return null;
			}

		} catch (Exception e) {
			throw new PuestoException("Ocurrio un error al consultar los puesto, por favor intente mas tarde.", 404);
		}
	}

	/**
	 * Metodo que realiza el guardado de los puestos.
	 */
	@Override
	public void guardarPuesto(puestoDto puesto) throws PuestoException {

		if (puesto != null) {
			try {
				PuestoEntity nuevoPuesto = new PuestoEntity();
				nuevoPuesto.setDescripcion(puesto.getDescripcion());
				puestoRepository.save(nuevoPuesto);
			} catch (Exception e) {
				throw new PuestoException("Ocurrio un error al registrar nuevo puesto, Comuníquese con soporte.", 500);
			}
		} else {
			throw new PuestoException("Los datos ingresados para guardar no son correctos, intente nuevamente.", 409);
		}
	}

	/**
	 * Metodo encargado de la logica de negocio para guardar un nuevo puesto.
	 */
	@Override
	public String modificarPuesto(int puesto_id, puestoDto puesto) throws PuestoException {

		if (puesto_id > 0) {
			try {
				PuestoEntity puestoEntity = puestoRepository.findById(puesto_id);
				if (puestoEntity == null) {
					throw new PuestoException(
							"No se encontro puesto con el No.: " + puesto_id + " para modificar, intertar nuevamente.",
							409);
				}
				puestoEntity.setDescripcion(puesto.getDescripcion());
				puestoRepository.save(puestoEntity);
				return "Puesto modificado correctamente.";
			} catch (Exception e) {
				throw new PuestoException("Ocurrio un error al modificar el puesto con id " + puesto_id
						+ " favor de comunicarse con soporte.", 500);
			}
		}
		return null;
	}

	/**
	 * Metodo encargado de la logica de negocio para eliminar el puesto (baja
	 * logica).
	 */
	@Override
	public String eliminarpuesto(int puesto_id) throws PuestoException {

		if (puesto_id > 0) {
			try {
				PuestoEntity puesto = puestoRepository.findById(puesto_id);
				if (puesto.getActivo().booleanValue() == false) {
					return "El puesto con id: " + puesto_id + " ya se encuentra dado de baja.";
				} else {
					puesto.setActivo(false);
					puestoRepository.save(puesto);
					return "El puesto con id: " + puesto_id + " se dio de baja correctamente.";
				}
			} catch (Exception e) {
				throw new PuestoException("Ocurrio un error al eliminar el puesto con id: " + puesto_id
						+ " favor de intentar nuevamente.", 409);
			}
		} else {
			return "Favor de ingresar el id del puesto a eliminar.";
		}
	}

	@Override
	public List<puestoDto> buscarPuesto(int puesto_id) throws PuestoException {

		List<puestoDto> listaPuesto = new ArrayList<>();

		if (puesto_id > 0) {
			PuestoEntity puestoEntity = puestoRepository.findById(puesto_id);
			if (puestoEntity == null) {
				throw new PuestoException("No se encontro puesto con los datos proporcionados", 404);
			}
			puestoDto puesto = new puestoDto();
			puesto.setPuesto_id(puestoEntity.getPuestoId());
			puesto.setDescripcion(puestoEntity.getDescripcion());
			listaPuesto.add(puesto);
			return listaPuesto;
		} else {
			throw new PuestoException("No se encontro puesto con los datos proporcionados.", 404);
		}
	}

	/**
	 * Metodo encargado de generar la estructura del excel y extraer los datos de la
	 * base para generar el reporte
	 */
	@Override
	public void exportarPuestosAExcel(HttpServletResponse response) throws PuestoException {
		LOGGER.info("::GENERANDO ESTRUCTURA DE EXCEL Y EXTRAYENDO LOS DATOS::");

		HSSFWorkbook libro = new HSSFWorkbook();
		HSSFSheet hoja = libro.createSheet("PUESTOS");
		// SE DEFINE LA POSICION DE DONDE SE COMENZARA A ESCRIBIR EL REPORTE
		int inicio = 0;
		int fin = 0;
		// SE CONSTRUYE EL LAYOUT
		construirLayoutReporte(hoja, inicio, fin);
		// SE HACE EL LLENADO DEL REPORTE CON LA INFORMACION DE LA BASE DE DATOS
		llenarReporte(hoja, fin, fin, extrerPuestos());

		String nombre = "Puestos.xls";
		response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + nombre);
		response.setContentType("application/vnd.ms-excel");
		escribirReporte(response, hoja);
	}

	/**
	 * @param hoja
	 * @param lineaInicio
	 * @param lineaFin    Metodo encargado de la construccion y estrucctura del
	 *                    excel
	 */
	public void construirLayoutReporte(HSSFSheet hoja, int lineaInicio, int lineaFin) {
		// SE AGREGA EL TAMAÑO DE LAS COLUMNAS
		hoja.setColumnWidth(0, 5000);
		hoja.setColumnWidth(1, 5000);
		hoja.setColumnWidth(2, 5000);
		hoja.setColumnWidth(3, 5000);
		hoja.setColumnWidth(4, 5000);
		hoja.setColumnWidth(5, 5000);

		//generarTitulo(hoja, lineaInicio, lineaFin);
		generarEncabezados(hoja, lineaInicio, lineaFin);

	}

	/**
	 * Metodo encargado de crear el titulo del excel
	 * 
	 * @param worksheet
	 * @param startRowIndex
	 * @param startColIndex
	 */
	public void generarTitulo(HSSFSheet worksheet, int startRowIndex, int startColIndex) {
		// CREA EL ESTILO DE FUENTE PARA EL TITULO DEL REPORTE
		Font fontTitle = worksheet.getWorkbook().createFont();
		fontTitle.setFontHeight((short) 280);

		// CREA UN ESTILO DE CELDA PARA EL TITULO
		HSSFCellStyle cellStyleTitle = worksheet.getWorkbook().createCellStyle();
		cellStyleTitle.setWrapText(true);
		cellStyleTitle.setFont(fontTitle);

		// CREA EL TITULO DEL REPORTE
		HSSFRow rowTitle = worksheet.createRow((short) startRowIndex);
		rowTitle.setHeight((short) 500);
		HSSFCell cellTitle = rowTitle.createCell(startColIndex);
		cellTitle.setCellValue("REPORTE DE PUESTOS");
		cellTitle.setCellStyle(cellStyleTitle);

		// CREA LA FECHA EN LA CABECERA
		HSSFRow dateTitle = worksheet.createRow((short) startRowIndex + 1);
		HSSFCell cellDate = dateTitle.createCell(startColIndex);
		cellDate.setCellValue("FECHA: " + new Date());
	}

	/**
	 * Metodo encargado de generar los encabezados del excel
	 * 
	 * @param worksheet
	 * @param startRowIndex
	 * @param startColIndex
	 */
	public void generarEncabezados(HSSFSheet worksheet, int startRowIndex, int startColIndex) {
		// CREA EL ESTILO DE FUENTE PARA LOS ENCABEZADOS
		Font font = worksheet.getWorkbook().createFont();

		// CREA EL ESTILO DE LAS CELDAS
		HSSFCellStyle headerCellStyle = worksheet.getWorkbook().createCellStyle();
		headerCellStyle.setWrapText(true);
		headerCellStyle.setFont(font);
		headerCellStyle.getAlignment();

		// CREA LAS COLULMAS DEL REPORTE
		HSSFRow rowHeader = worksheet.createRow((short) startRowIndex);
		rowHeader.setHeight((short) 500);

		HSSFCell cell1 = rowHeader.createCell(startColIndex + 0);
		cell1.setCellValue("No. Puesto");
		cell1.setCellStyle(headerCellStyle);

		HSSFCell cell2 = rowHeader.createCell(startColIndex + 1);
		cell2.setCellValue("Descripcion");
		cell2.setCellStyle(headerCellStyle);
	}

	/**
	 * Metodo encargado de llenar el documento excel con los datos proporcionados de
	 * la base.
	 * 
	 * @param worksheet
	 * @param startRowIndex
	 * @param startColIndex
	 * @param datasource
	 * @throws ExceptionGlobal
	 */
	public void llenarReporte(HSSFSheet worksheet, int startRowIndex, int startColIndex, List<puestoDto> datasource)
			throws PuestoException {

		if (datasource != null) {
			// Linea dode comenzara
			startRowIndex += 1;

			// SE CREA ESTILO DE LAS CELDAS
			HSSFCellStyle bodyCellStyle = worksheet.getWorkbook().createCellStyle();
			bodyCellStyle.setWrapText(true);

			// SE CREA EL CUERPO DEL REPORTE
			try {
				for (int i = startRowIndex; i + startRowIndex - 1 < datasource.size() + 1; i++) {
					// CREA UNA NUEVA LINEA
					HSSFRow row = worksheet.createRow((short) i + 1);

					// SE LE ASIGNA EL VALOR A LA CELDA
					HSSFCell cell1 = row.createCell(startColIndex + 0);
					cell1.setCellValue(datasource.get(i - 1).getPuesto_id());
					cell1.setCellStyle(bodyCellStyle);

					HSSFCell cell2 = row.createCell(startColIndex + 1);
					cell2.setCellValue(datasource.get(i - 1).getDescripcion());
					cell2.setCellStyle(bodyCellStyle);

//					HSSFCell cell3 = row.createCell(startColIndex + 2);
//					cell3.setCellValue(datasource.get(i - 2).getActivo());
//					cell3.setCellStyle(bodyCellStyle);
				}

			} catch (Exception e) {
				LOGGER.error(":: ERROR EN LA GENERACION DEL REPORTE ===> " + e.getMessage() + "::");
				throw new PuestoException("Ocurrio un error generando el reporte, comuniquese con soporte.", 500);
			}
		} else {
			throw new PuestoException("No se pudo extraer los datos para generar el reporte, comuniquese con soporte.",
					404);
		}

	}

	/**
	 * Metodo encargado de escribir y retornar el reporte en excel.
	 * 
	 * @param response
	 * @param hoja
	 * @throws PuestoException
	 */
	public void escribirReporte(HttpServletResponse response, HSSFSheet hoja) throws PuestoException {
		LOGGER.info("::ESCRIBIENDO DATOS EN EXCEL::");
		try {
			ServletOutputStream reporteGenerado = response.getOutputStream();
			hoja.getWorkbook().write(reporteGenerado);
			reporteGenerado.flush();
		} catch (Exception e) {
			LOGGER.error("::ERROR AL GENERAR EL REPORTE ===> " + e.getMessage() + " ::");
			throw new PuestoException("Ocurrio un error al generar el reporte, favor de comunicarse con soporte.", 500);
		}
	}
}
