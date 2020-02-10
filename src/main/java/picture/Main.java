package picture;

public class Main {

    public static void main(String[] args) {
        Picture pic;
        Process process;
        /* We will use the first argument to determine how to handle the rest of the
         * input arguments. */
        try {
            switch (args[0]) {
                case "invert":
                    pic = loadPicture(args[1]);
                    process = new Process(pic);
                    process.invert();
                    savePicture(process.getPicture(), args[2]);
                    break;
                case "grayscale":
                    pic = loadPicture(args[1]);
                    process = new Process(pic);
                    process.grayscale();
                    savePicture(process.getPicture(), args[2]);
                    break;
                case "rotate":
                    pic = loadPicture(args[2]);
                    process = new Process(pic);
                    switch (args[1]) {
                        case "90":
                            process.rotate90();
                            break;
                        case "180":
                            process.rotate180();
                            break;
                        case "270":
                            process.rotate270();
                            break;
                        default:
                            invalidInput();
                    }
                    savePicture(process.getPicture(), args[3]);
                    break;
                case "flip":
                    pic = loadPicture(args[2]);
                    process = new Process(pic);
                    switch (args[1]) {
                        case "H":
                            process.flipHorizontal();
                            break;
                        case "V":
                            process.flipVertical();
                            break;
                        default:
                            invalidInput();
                    }
                    savePicture(process.getPicture(), args[3]);
                    break;
                case "blend":
                    /* The index before the outputIndex is the last input index. */
                    int numInputs = args.length - 2;
                    Picture[] pics = new Picture[numInputs];
                    int minWidth = Integer.MAX_VALUE - 1;
                    int minHeight = Integer.MAX_VALUE - 1;
                    /* We both load all images and calculate the minimum width and
                     * height. */
                    for (int i = 0; i < numInputs; i++) {
                        pics[i] = loadPicture(args[i + 1]);
                        minWidth = Math.min(minWidth, pics[i].getWidth());
                        minHeight = Math.min(minHeight, pics[i].getHeight());
                    }
                    process = new Process(Utils.createPicture(minWidth, minHeight));
                    process.blend(pics);
                    savePicture(process.getPicture(), args[args.length - 1]);
                    break;
                case "blur":
                    pic = loadPicture(args[1]);
                    process = new Process(pic);
                    process.blur();
                    savePicture(process.getPicture(), args[2]);
                    break;
                default:
                    invalidInput();
            }
        } catch (IndexOutOfBoundsException e) {
            /* We only use array accesses for input arguments. Therefore, an out of
             * bounds error must be an erroneous input. */
            System.out.println("Error: Invalid number of input arguments!");
            System.exit(1);
        }

    }

    /* Function to allow easy error printing and exiting. */
    private static void invalidInput() {
        System.out.println("Error: Invalid input command!");
        System.exit(1);
    }

    /* A wrapper for savePicture function in Utils to consider for erroneous
     * saves. */
    private static void savePicture(Picture picture, String destination) {
        boolean success = Utils.savePicture(picture, destination);
        if (!success) {
            System.out.println("Error: Failed to save image.");
            System.exit(1);
        }
    }

    /* A wrapper for loadPicture function in Utils to consider for erroneous
     * loads. */
    private static Picture loadPicture(String destination) {
        Picture pic = Utils.loadPicture(destination);
        if (pic == null) {
            System.out.println("Error: Failed to load image.");
            System.exit(1);
        }
        return pic;
    }
}
