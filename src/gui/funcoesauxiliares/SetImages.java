package gui.funcoesauxiliares;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.scilab.forge.jlatexmath.TeXConstants;
import org.scilab.forge.jlatexmath.TeXFormula;

import java.awt.image.BufferedImage;
import java.util.List;

public class SetImages {

	private Integer l = 0;
	private Double k = 1.0;

	public void setImages(List<String> listStrings, List<ImageView> listImages, List<Boolean >small) {

		String sysOs = System.getProperty("os.name");
		String[] newOs = sysOs.split(" ");

		if (newOs[0].contentEquals("Windows")) {
			k = 1.0;
			l = 19;
			for (int i = 0; i < listStrings.size(); i++) {
				formatImageViews(listStrings.get(i), listImages.get(i), l);
			}

		} else {
			k = 0.65;
			l = 35;
			for (int i = 0; i < listStrings.size(); i++) {
				formatImageViews(listStrings.get(i), listImages.get(i), l);
				
				listImages.get(i).setFitHeight(l * k);
				if(small.get(i) == true) {
					listImages.get(i).setFitHeight(l * k * 0.8);
				}
			}

		}

	}

	private void formatImageViews(String string, ImageView imageView, Integer k) {

		final TeXFormula tex = new TeXFormula(string);
		java.awt.Image imagem = tex.createBufferedImage(TeXConstants.STYLE_TEXT, k, java.awt.Color.BLACK, null);
		Image fx = SwingFXUtils.toFXImage((BufferedImage) imagem, null);
		imageView.setImage(fx);
		imageView.setFitHeight(fx.getHeight());
		imageView.setPreserveRatio(true);
	}

}
