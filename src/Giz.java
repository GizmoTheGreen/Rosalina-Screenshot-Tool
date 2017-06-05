import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;

public class Giz {

	static File ChooseFolder(Shell shell) {
		DirectoryDialog dialog = new DirectoryDialog(shell, SWT.OPEN);
		dialog.setFilterPath(".");
		String folder = dialog.open();
		if (folder != null) {
			return new File(folder);
		} else {
			return null;
		}
	}

	static void SaveFile(Shell shell, Image img) {
		FileDialog dialog = new FileDialog(shell, SWT.SAVE);
		String[] ext = { "*.png" };
		dialog.setFilterExtensions(ext);
		dialog.setOverwrite(true);
		
		String savefile1 = dialog.open(); 
		if(!savefile1.contains(".png")){
			savefile1 = savefile1 + ".png";
		}
		File savefile = new File(savefile1);
		ImageLoader saver = new ImageLoader();
		saver.data = new ImageData[] { img.getImageData() };
		saver.save(savefile.getAbsolutePath(), SWT.IMAGE_PNG);
	}

	static List<String> GetFileList(File folder) {
		List<File> fileList;
		List<String> numberList = new ArrayList<String>();

		fileList = Arrays.asList(folder.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File arg0, String filename) {
				String Regex = "(top_)(\\d\\d\\d\\d)(\\.bmp)";
				return filename.matches(Regex);
			}
		}));

		// Collections.
		// Collections.sort(botList);

		if (fileList != null) {
			fileList.forEach(listItem -> {
				String filePathString = folder + "\\top_" + listItem.getName().toString().substring(4, 8) + ".bmp";
				System.out.println(filePathString);
				String filePathString2 = folder + "\\bot_" + listItem.getName().toString().substring(4, 8) + ".bmp";
				File f = new File(filePathString);
				File f2 = new File(filePathString2);
				if ((f.exists() && f2.exists()) && (!f.isDirectory() && !f2.isDirectory())) {
					numberList.add(listItem.getName().toString().substring(4, 8));
				}
			});
		}
		return numberList;
	}

	static Image OpenScreenshot(File folder, String[] strings) {
		String filePathString = folder + "\\top_" + strings[0] + ".bmp";
		String filePathString2 = folder + "\\bot_" + strings[0] + ".bmp";
		Image top = new Image(Display.getDefault(), filePathString);
		Image bot = new Image(Display.getDefault(), filePathString2);
		Image img = new Image(Display.getDefault(), 400, 480);
		GC gc = new GC(img);
		gc.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_BLACK));
		gc.fillRectangle(0, 0, 400, 480);
		gc.setForeground(new Color(Display.getDefault(), 0, 0, 0, 0));
		gc.drawPoint(0, 479);
		gc.drawImage(top, 0, 0);
		gc.drawImage(bot, 40, 240);

		return img;
	}

}
