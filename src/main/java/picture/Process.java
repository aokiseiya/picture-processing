package picture;

public class Process {

    private Picture pic;

    public Process(Picture pic) {
        this.pic = pic;
    }

    public Picture getPicture()
    {
        return pic;
    }

    /* This process is done by replacing each pixels' original intensity value
     * of each primary colour, c, with the intensity (255 − c). */
    public void invert() {
        for (int y = 0; y < pic.getHeight(); y++) {
            for (int x = 0; x < pic.getWidth(); x++) {
                Color pixel = pic.getPixel(x, y);
                pixel.setRed(255 - pixel.getRed());
                pixel.setBlue(255 - pixel.getBlue());
                pixel.setGreen(255 - pixel.getGreen());
                pic.setPixel(x, y, pixel);
            }
        }
    }

    /* This process is done by replacing each pixels' original intensity value
     * of each primary colour, c, with the average intensity of each primary colours. */
    public void grayscale() {
        for (int y = 0; y < pic.getHeight(); y++) {
            for (int x = 0; x < pic.getWidth(); x++) {
                Color pixel = pic.getPixel(x, y);
                int avg = (pixel.getRed() + pixel.getGreen() + pixel.getBlue()) / 3;
                pixel.setRed(avg);
                pixel.setBlue(avg);
                pixel.setGreen(avg);
                pic.setPixel(x, y, pixel);
            }
        }
    }

    /* This process is done by replacing each pixels with that of the horizontal opposite. */
    public void flipHorizontal() {
        for (int y = 0; y < pic.getHeight(); y++) {
            for (int x = 0; x < (pic.getWidth() - 1) / 2; x++) {
                int xToSwap = pic.getWidth() - 1 - x;
                Color pixel = pic.getPixel(x, y);
                pic.setPixel(x, y, pic.getPixel(xToSwap, y));
                pic.setPixel(xToSwap, y, pixel);
            }
        }
    }

    /* This process is done by replacing each pixels with that of the vertical opposite. */
    public void flipVertical() {
        for (int x = 0; x < pic.getWidth(); x++) {
            for (int y = 0; y <= (pic.getHeight() - 1) / 2; y++) {
                int yToSwap = pic.getHeight() - 1 - y;
                Color pixel = pic.getPixel(x, y);
                pic.setPixel(x, y, pic.getPixel(x, yToSwap));
                pic.setPixel(x, yToSwap, pixel);
            }
        }
    }

    /* The rotate functions will simply create a new picture of appropriate dimensions and put the
     * appropriate pixels. */
    public void rotate90() {
        Picture output = Utils.createPicture(pic.getHeight(),pic.getWidth());
        for (int y = 0; y < pic.getHeight(); y++) {
            for (int x = 0; x < pic.getWidth(); x++) {
                Color pixel = pic.getPixel(x, y);
                output.setPixel(pic.getHeight() - 1 - y, x, pixel);
            }
        }
        pic = output;
    }

    public void rotate180() {
        Picture output = Utils.createPicture(pic.getWidth(),pic.getHeight());
        for (int y = 0; y < pic.getHeight(); y++) {
            for (int x = 0; x < pic.getWidth(); x++) {
                Color pixel = pic.getPixel(x, y);
                output.setPixel(pic.getWidth() - 1 - x, pic.getHeight() - 1 - y, pixel);
            }
        }
        pic = output;
    }

    public void rotate270() {
        Picture output = Utils.createPicture(pic.getHeight(),pic.getWidth());
        for (int y = 0; y < pic.getHeight(); y++) {
            for (int x = 0; x < pic.getWidth(); x++) {
                Color pixel = pic.getPixel(x, y);
                output.setPixel(y, pic.getWidth() - 1 - x, pixel);
            }
        }
        pic = output;
    }

    /* This process is a special case where the pic property is assumed to have the required
     * dimensions of the blend process, and the blend process is carried out using the input
     * array of Picture objects. */
    public void blend(Picture[] pics) {
        for (int y = 0; y < pic.getHeight(); y++) {
            for (int x = 0; x < pic.getWidth(); x++) {
                int avgRed = 0;
                int avgGreen = 0;
                int avgBlue = 0;
                for (Picture picture : pics) {
                    Color pixel = picture.getPixel(x, y);
                    avgRed += pixel.getRed();
                    avgGreen += pixel.getGreen();
                    avgBlue += pixel.getBlue();
                }
                avgRed /= pics.length;
                avgGreen /= pics.length;
                avgBlue /= pics.length;
                pic.setPixel(x, y, new Color(avgRed, avgGreen, avgBlue));
            }
        }
    }

    /* The blur process will call blurPixel on every every non-edge pixel. */
    public void blur() {
        Picture output = Utils.createPicture(pic.getWidth(), pic.getHeight());
        for (int y = 0; y < pic.getHeight(); y++) {
            for (int x = 0; x < pic.getWidth(); x++) {
                output.setPixel(x, y, blurPixel(x, y));
            }
        }
        pic = output;
    }

    /* This helper function simply "blurs" a single pixel - i.e. calculates the average RBG
     * components of itself and its 8 pixels around it. Input pixel is assumed to be a non-edge
     * coordinate. */
    private Color blurPixel(int xInput, int yInput) {
        int avgRed = 0;
        int avgGreen = 0;
        int avgBlue = 0;
        for (int y = yInput - 1; y <= yInput + 1; y++) {
            for (int x = xInput - 1; x <= xInput + 1; x++) {
                if (pic.contains(x, y)) {
                    Color pixel = pic.getPixel(x, y);
                    avgRed += pixel.getRed();
                    avgGreen += pixel.getGreen();
                    avgBlue += pixel.getBlue();
                }
                else {
                    return pic.getPixel(xInput, yInput);
                }
            }
        }
        avgRed /= 9;
        avgGreen /= 9;
        avgBlue /= 9;
        return new Color(avgRed, avgGreen, avgBlue);
    }

}