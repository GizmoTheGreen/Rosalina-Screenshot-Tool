import java.io.File;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.List;
import java.util.Arrays;

import javax.imageio.ImageIO;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

@SuppressWarnings("unused")
public class Main {

	File folder;
	Image current;
	ImageIO saveImage;
	
	protected Shell shlRosalinaScreenshotTool;

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Main window = new Main();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shlRosalinaScreenshotTool.open();
		shlRosalinaScreenshotTool.layout();
		while (!shlRosalinaScreenshotTool.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shlRosalinaScreenshotTool = new Shell();
		shlRosalinaScreenshotTool.setMinimumSize(new Point(420, 500));
		shlRosalinaScreenshotTool.setSize(614, 541);
		shlRosalinaScreenshotTool.setText("Rosalina Screenshot Tool");
		
		Canvas canvas = new Canvas(shlRosalinaScreenshotTool, SWT.NONE);
		canvas.setBounds(188, 10, 400, 480);
		
		List list = new List(shlRosalinaScreenshotTool, SWT.BORDER);
	
		list.setBounds(10, 41, 172, 418);
		
		
		Button btnChooseFolder = new Button(shlRosalinaScreenshotTool, SWT.NONE);
		btnChooseFolder.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				File folder2 = Giz.ChooseFolder(shlRosalinaScreenshotTool);
				if (folder2 != null) {
					folder = folder2;
					String[] items = Giz.GetFileList(folder).toArray(new String[0]);
					list.setItems(items);
				}
			}
		});
		btnChooseFolder.setBounds(10, 10, 172, 25);
		btnChooseFolder.setText("Choose Folder");
		
		Button btnSaveScreenshot = new Button(shlRosalinaScreenshotTool, SWT.NONE);
		btnSaveScreenshot.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				if(current!=null){
				Giz.SaveFile(shlRosalinaScreenshotTool, current);
				}
			}
		});
		btnSaveScreenshot.setBounds(10, 465, 172, 25);
		btnSaveScreenshot.setText("Save Screenshot");

		list.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(folder!=null && list.getSelection().length>0){
				current = Giz.OpenScreenshot(folder, list.getSelection());
				canvas.setBackgroundImage(current);
				
				}
			}
		});

	}
}
