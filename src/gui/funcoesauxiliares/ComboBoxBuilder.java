package gui.funcoesauxiliares;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import org.scilab.forge.jlatexmath.TeXConstants;
import org.scilab.forge.jlatexmath.TeXFormula;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class ComboBoxBuilder {

	ConvertToFxImage convertToFxImage = new ConvertToFxImage();
	
	private Integer k;

	private void opSys() {
		String sysOs = System.getProperty("os.name");
		String[] newOs = sysOs.split(" ");
		if (newOs[0].contentEquals("Windows")) {
			k = 16;
		} else {
			k = 25;
		}
	}

	public List<Text> updateCBCinetica() {

		List<Text> displayCinetica = new ArrayList<Text>();

		Text textPT = new Text();
		textPT.setText("Power-Law");
		textPT.setFont(Font.font("System", 13));

		displayCinetica.add(textPT);

		Text textER = new Text();
		textER.setText("Eley-Rideal");
		textER.setFont(Font.font("System", 13));
		displayCinetica.add(textER);

		Text textLH = new Text();
		textLH.setText("Langmuir-Hinshelwood");
		textLH.setFont(Font.font("System", 13));
		displayCinetica.add(textLH);

		return displayCinetica;
	}

	public List<Text> updateCBMaterialExt() {

		List<Text> displayMaterialExterno = new ArrayList<Text>();

		Text textAgua = new Text();
		textAgua.setText("Water");
		textAgua.setFont(Font.font("System", 13));
		displayMaterialExterno.add(textAgua);

		Text textAr = new Text();
		textAr.setText("Air");
		textAr.setFont(Font.font("System", 13));
		displayMaterialExterno.add(textAr);

		return displayMaterialExterno;
	}

	public List<Text> updateCBMaterialReator() {

		List<Text> displayMaterialReator = new ArrayList<Text>();

		Text textAco = new Text();
		textAco.setText("Steel");
		textAco.setFont(Font.font("System", 13));
		displayMaterialReator.add(textAco);

		return displayMaterialReator;
	}
	
	public List<Image> updateCBComprimento() {
		
		opSys();

		List<Image> displayComprimento = new ArrayList<Image>();
	
		final String textM = "\\text{m}";
		final TeXFormula mTex = new TeXFormula(textM);
		BufferedImage mImage = (BufferedImage) mTex.createBufferedImage(TeXConstants.STYLE_TEXT, k,
				java.awt.Color.BLACK, null);
		Image mFX = convertToFxImage.convertToFxImage(mImage);
		displayComprimento.add(mFX);
		
		final String textCM = "\\text{cm}";
		final TeXFormula cmTex = new TeXFormula(textCM);
		BufferedImage cmImage = (BufferedImage) cmTex.createBufferedImage(TeXConstants.STYLE_TEXT, k,
				java.awt.Color.BLACK, null);
		Image cmFX = convertToFxImage.convertToFxImage(cmImage);
		displayComprimento.add(cmFX);
		
		final String textMM = "\\text{mm}";
		final TeXFormula mmTex = new TeXFormula(textMM);
		BufferedImage mmImage = (BufferedImage) mmTex.createBufferedImage(TeXConstants.STYLE_TEXT, k,
				java.awt.Color.BLACK, null);
		Image mmFX = convertToFxImage.convertToFxImage(mmImage);
		displayComprimento.add(mmFX);

	
		
	
		
		
		return displayComprimento;
	}

	public List<Image> updateCBTemperatura() {

		opSys();

		List<Image> displayTemperatura = new ArrayList<Image>();

		final String textKelvin = "\\text{K}";
		final TeXFormula kelvinTex = new TeXFormula(textKelvin);
		BufferedImage kelvinImage = (BufferedImage) kelvinTex.createBufferedImage(TeXConstants.STYLE_TEXT, k,
				java.awt.Color.BLACK, null);
		Image kelvinFX = convertToFxImage.convertToFxImage(kelvinImage);
		displayTemperatura.add(kelvinFX);

		final String textCelcius = "\\text{^{\\circ}C}";
		final TeXFormula celciusTex = new TeXFormula(textCelcius);
		BufferedImage celciusImage = (BufferedImage) celciusTex.createBufferedImage(TeXConstants.STYLE_TEXT, k,
				java.awt.Color.BLACK, null);
		Image celciusFX = convertToFxImage.convertToFxImage(celciusImage);
		displayTemperatura.add(celciusFX);

		final String textFahrenheit = "\\text{^{\\circ}F}";
		final TeXFormula fahrenheitTex = new TeXFormula(textFahrenheit);
		BufferedImage fahrenheitImage = (BufferedImage) fahrenheitTex.createBufferedImage(TeXConstants.STYLE_TEXT, k,
				java.awt.Color.BLACK, null);
		Image fahrenheitFX = convertToFxImage.convertToFxImage(fahrenheitImage);
		displayTemperatura.add(fahrenheitFX);

		return displayTemperatura;

	}
	
	public List<Image> updateCBUTC() {

		opSys();

		List<Image> displayUTC = new ArrayList<Image>();

		final String texTUTC = "\\text{kJ}/\\text{m}^2\\text{sK}";
		final TeXFormula formulaUTC = new TeXFormula(texTUTC);
		BufferedImage UTCImage = (BufferedImage) formulaUTC.createBufferedImage(TeXConstants.STYLE_TEXT, k,
				java.awt.Color.BLACK, null);
		Image UTCFX = convertToFxImage.convertToFxImage(UTCImage);
		displayUTC.add(UTCFX);

		return displayUTC;

	}


	public List<Image> updateCBResultados() {

		opSys();

		List<Image> displayResultados = new ArrayList<Image>();

		final String res1 = "\\text{mol}/\\mbox{kg}_{cat}\\mbox{h}";
		final TeXFormula res1Tex = new TeXFormula(res1);
		java.awt.Image res1Image = res1Tex.createBufferedImage(TeXConstants.STYLE_TEXT, k, java.awt.Color.BLACK, null);
		Image res1FX = SwingFXUtils.toFXImage((BufferedImage) res1Image, null);
		displayResultados.add(res1FX);

	/*	final String res2 = "\\mbox{mol}_{CH_4}/\\mbox{kg}_{cat}\\mbox{h}";
		final TeXFormula res2Tex = new TeXFormula(res2);
		java.awt.Image res2Image = res2Tex.createBufferedImage(TeXConstants.STYLE_TEXT, k, java.awt.Color.BLACK, null);
		Image res2FX = SwingFXUtils.toFXImage((BufferedImage) res2Image, null);
		displayResultados.add(res2FX);

		final String res3 = "\\mbox{mol}_{CH_4}/\\mbox{kg}_{cat}\\mbox{min}";
		final TeXFormula res3Tex = new TeXFormula(res3);
		java.awt.Image res3Image = res3Tex.createBufferedImage(TeXConstants.STYLE_TEXT, k, java.awt.Color.BLACK, null);
		Image res3FX = SwingFXUtils.toFXImage((BufferedImage) res3Image, null);
		displayResultados.add(res3FX);

		final String res4 = "\\mbox{mol}_{CH_4}/\\mbox{kg}_{cat}\\mbox{s}";
		final TeXFormula res4Tex = new TeXFormula(res4);
		java.awt.Image res4Image = res4Tex.createBufferedImage(TeXConstants.STYLE_TEXT, k, java.awt.Color.BLACK, null);
		Image res4FX = SwingFXUtils.toFXImage((BufferedImage) res4Image, null);
		displayResultados.add(res4FX);
*/
		return displayResultados;
	}

	public List<Text> updateCBMetodo() {

		List<Text> displayMaterialReator = new ArrayList<Text>();

		Text textEnxame = new Text();
		textEnxame.setText("Particle Swarm");
		textEnxame.setFont(Font.font("System", 13));
		displayMaterialReator.add(textEnxame);

		/*  Reativar para habilitar outros métodos de estimação
		Text textLM = new Text();
		textLM.setText("Levenberg-Marquardt");
		textLM.setFont(Font.font("System", 13));
		displayMaterialReator.add(textLM);

		Text textNM = new Text();
		textNM.setText("Newton Modificado");
		textNM.setFont(Font.font("System", 13));
		displayMaterialReator.add(textNM);*/

		return displayMaterialReator;
	}
	
	public List<Text> updateCBVazao() {

		List<Text> displayMaterialReator = new ArrayList<Text>();

		Text textEnxame = new Text();
		textEnxame.setText("Vazão Volumétrica");
		textEnxame.setFont(Font.font("System", 13));
		displayMaterialReator.add(textEnxame);

		Text textLM = new Text();
		textLM.setText("Vazão Molar");
		textLM.setFont(Font.font("System", 13));
		displayMaterialReator.add(textLM);

		Text textNM = new Text();
		textNM.setText("Vazão Mássica");
		textNM.setFont(Font.font("System", 13));
		displayMaterialReator.add(textNM);

		return displayMaterialReator;
	}

	public List<Image> updateCBVazaoVol() {

		opSys();

		List<Image> displayVazao = new ArrayList<Image>();
		
		final String mSegundo = "\\text{m^3/s}";
		final TeXFormula mSegundoTex = new TeXFormula(mSegundo);
		BufferedImage mSegundoImage = (BufferedImage) mSegundoTex.createBufferedImage(TeXConstants.STYLE_TEXT, k,
				java.awt.Color.BLACK, null);
		Image mSegundoFX = convertToFxImage.convertToFxImage(mSegundoImage);
		displayVazao.add(mSegundoFX);
		
		final String mMinuto = "\\text{m^3/min}";
		final TeXFormula mMinutoTex = new TeXFormula(mMinuto);
		BufferedImage mMinutoImage = (BufferedImage) mMinutoTex.createBufferedImage(TeXConstants.STYLE_TEXT, k,
				java.awt.Color.BLACK, null);
		Image mMinutoFX = convertToFxImage.convertToFxImage(mMinutoImage);
		displayVazao.add(mMinutoFX);
		

		final String mHora = "\\mbox{m^3/h}";
		final TeXFormula mHoraTex = new TeXFormula(mHora);
		BufferedImage mHoraImage = (BufferedImage) mHoraTex.createBufferedImage(TeXConstants.STYLE_TEXT, k,
				java.awt.Color.BLACK, null);
		Image mHoraFX = convertToFxImage.convertToFxImage(mHoraImage);

		displayVazao.add(mHoraFX);


		final String mlSegundo = "\\text{mL/s}";
		final TeXFormula mlSegundoTex = new TeXFormula(mlSegundo);
		BufferedImage mlSegundoImage = (BufferedImage) mlSegundoTex.createBufferedImage(TeXConstants.STYLE_TEXT, k,
				java.awt.Color.BLACK, null);
		Image mlSegundoFX = convertToFxImage.convertToFxImage(mlSegundoImage);
		displayVazao.add(mlSegundoFX);
		
		final String mlHoraMinuto = "\\text{mL/min}";
		final TeXFormula mlHoraMinutoTex = new TeXFormula(mlHoraMinuto);
		BufferedImage mlHoraMinutoImage = (BufferedImage) mlHoraMinutoTex.createBufferedImage(TeXConstants.STYLE_TEXT,
				k, java.awt.Color.BLACK, null);
		Image mlHoraMinutoFX = convertToFxImage.convertToFxImage(mlHoraMinutoImage);
		displayVazao.add(mlHoraMinutoFX);
	

		final String mlHora = "\\text{mL/h}";
		final TeXFormula mlHoraTex = new TeXFormula(mlHora);
		BufferedImage mlHoraImage = (BufferedImage) mlHoraTex.createBufferedImage(TeXConstants.STYLE_TEXT, k,
				java.awt.Color.BLACK, null);
		Image mlHoraFX = convertToFxImage.convertToFxImage(mlHoraImage);
		displayVazao.add(mlHoraFX);

		



		return displayVazao;

	}
	
	public List<Image> updateCBVazaoMolar() {

		opSys();

		List<Image> displayVazao = new ArrayList<Image>();

		final String mHora = "\\mbox{mol/h}";
		final TeXFormula mHoraTex = new TeXFormula(mHora);
		BufferedImage mHoraImage = (BufferedImage) mHoraTex.createBufferedImage(TeXConstants.STYLE_TEXT, k,
				java.awt.Color.BLACK, null);
		Image mHoraFX = convertToFxImage.convertToFxImage(mHoraImage);

		displayVazao.add(mHoraFX);

		final String mMinuto = "\\text{mol/min}";
		final TeXFormula mMinutoTex = new TeXFormula(mMinuto);
		BufferedImage mMinutoImage = (BufferedImage) mMinutoTex.createBufferedImage(TeXConstants.STYLE_TEXT, k,
				java.awt.Color.BLACK, null);
		Image mMinutoFX = convertToFxImage.convertToFxImage(mMinutoImage);
		displayVazao.add(mMinutoFX);

		final String mSegundo = "\\text{mol/s}";
		final TeXFormula mSegundoTex = new TeXFormula(mSegundo);
		BufferedImage mSegundoImage = (BufferedImage) mSegundoTex.createBufferedImage(TeXConstants.STYLE_TEXT, k,
				java.awt.Color.BLACK, null);
		Image mSegundoFX = convertToFxImage.convertToFxImage(mSegundoImage);
		displayVazao.add(mSegundoFX);

		final String mlHora = "\\text{kmol/h}";
		final TeXFormula mlHoraTex = new TeXFormula(mlHora);
		BufferedImage mlHoraImage = (BufferedImage) mlHoraTex.createBufferedImage(TeXConstants.STYLE_TEXT, k,
				java.awt.Color.BLACK, null);
		Image mlHoraFX = convertToFxImage.convertToFxImage(mlHoraImage);
		displayVazao.add(mlHoraFX);

		final String mlHoraMinuto = "\\text{kmol/min}";
		final TeXFormula mlHoraMinutoTex = new TeXFormula(mlHoraMinuto);
		BufferedImage mlHoraMinutoImage = (BufferedImage) mlHoraMinutoTex.createBufferedImage(TeXConstants.STYLE_TEXT,
				k, java.awt.Color.BLACK, null);
		Image mlHoraMinutoFX = convertToFxImage.convertToFxImage(mlHoraMinutoImage);
		displayVazao.add(mlHoraMinutoFX);

		final String mlSegundo = "\\text{kmol/s}";
		final TeXFormula mlSegundoTex = new TeXFormula(mlSegundo);
		BufferedImage mlSegundoImage = (BufferedImage) mlSegundoTex.createBufferedImage(TeXConstants.STYLE_TEXT, k,
				java.awt.Color.BLACK, null);
		Image mlSegundoFX = convertToFxImage.convertToFxImage(mlSegundoImage);
		displayVazao.add(mlSegundoFX);

		return displayVazao;

	}
	
	public List<Image> updateCBVazaoMassica() {

		opSys();

		List<Image> displayVazao = new ArrayList<Image>();

		final String mHora = "\\mbox{kg/h}";
		final TeXFormula mHoraTex = new TeXFormula(mHora);
		BufferedImage mHoraImage = (BufferedImage) mHoraTex.createBufferedImage(TeXConstants.STYLE_TEXT, k,
				java.awt.Color.BLACK, null);
		Image mHoraFX = convertToFxImage.convertToFxImage(mHoraImage);

		displayVazao.add(mHoraFX);

		final String mMinuto = "\\text{kg/min}";
		final TeXFormula mMinutoTex = new TeXFormula(mMinuto);
		BufferedImage mMinutoImage = (BufferedImage) mMinutoTex.createBufferedImage(TeXConstants.STYLE_TEXT, k,
				java.awt.Color.BLACK, null);
		Image mMinutoFX = convertToFxImage.convertToFxImage(mMinutoImage);
		displayVazao.add(mMinutoFX);

		final String mSegundo = "\\text{kg/s}";
		final TeXFormula mSegundoTex = new TeXFormula(mSegundo);
		BufferedImage mSegundoImage = (BufferedImage) mSegundoTex.createBufferedImage(TeXConstants.STYLE_TEXT, k,
				java.awt.Color.BLACK, null);
		Image mSegundoFX = convertToFxImage.convertToFxImage(mSegundoImage);
		displayVazao.add(mSegundoFX);

		final String mlHora = "\\text{g/h}";
		final TeXFormula mlHoraTex = new TeXFormula(mlHora);
		BufferedImage mlHoraImage = (BufferedImage) mlHoraTex.createBufferedImage(TeXConstants.STYLE_TEXT, k,
				java.awt.Color.BLACK, null);
		Image mlHoraFX = convertToFxImage.convertToFxImage(mlHoraImage);
		displayVazao.add(mlHoraFX);

		final String mlHoraMinuto = "\\text{g/min}";
		final TeXFormula mlHoraMinutoTex = new TeXFormula(mlHoraMinuto);
		BufferedImage mlHoraMinutoImage = (BufferedImage) mlHoraMinutoTex.createBufferedImage(TeXConstants.STYLE_TEXT,
				k, java.awt.Color.BLACK, null);
		Image mlHoraMinutoFX = convertToFxImage.convertToFxImage(mlHoraMinutoImage);
		displayVazao.add(mlHoraMinutoFX);

		final String mlSegundo = "\\text{g/s}";
		final TeXFormula mlSegundoTex = new TeXFormula(mlSegundo);
		BufferedImage mlSegundoImage = (BufferedImage) mlSegundoTex.createBufferedImage(TeXConstants.STYLE_TEXT, k,
				java.awt.Color.BLACK, null);
		Image mlSegundoFX = convertToFxImage.convertToFxImage(mlSegundoImage);
		displayVazao.add(mlSegundoFX);

		return displayVazao;

	}
	
	public List<Text> updateCBPressure() {

		List<Text> displayMaterialReator = new ArrayList<Text>();

		Text textPressure = new Text();
		textPressure.setText("bar");
		textPressure.setFont(Font.font("System", 13));
		displayMaterialReator.add(textPressure);

		return displayMaterialReator;
	}
	
}
