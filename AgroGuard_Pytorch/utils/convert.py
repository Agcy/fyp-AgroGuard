def convert_annotation_format(input_file, output_file):
    """
    Convert the annotation format from multi-line per image to single-line per image.

    Args:
    - input_file: Path to the input annotation file.
    - output_file: Path where the converted annotation file will be saved.
    """
    with open(input_file, 'r') as f:
        lines = f.readlines()

    current_image = None
    annotations = []
    converted_lines = []

    for line in lines:
        parts = line.strip().split()
        if len(parts) == 6:  # New image and its first annotation
            if current_image is not None:
                # Save previous image and its annotations
                converted_lines.append(current_image + " " + " ".join(annotations))
                annotations = []
            current_image = parts[0]  # Update current image
            annotations.append(",".join(parts[1:]))  # Add first annotation
        elif len(parts) == 5:  # Additional annotations for the current image
            annotations.append(",".join(parts))

    # Don't forget to add the last image and its annotations
    if current_image is not None:
        converted_lines.append(current_image + " " + " ".join(annotations))

    # Save the converted annotations to the output file
    with open(output_file, 'w') as f:
        for line in converted_lines:
            f.write(line + "\n")


# Example usage
input_file = 'D:\BIRD\year4\FinalYearProject\AgroGuard\AgroGuard_Pytorch\dataset\ImageSets\Main\\test.txt'
output_file = 'D:\BIRD\year4\FinalYearProject\AgroGuard\AgroGuard_Pytorch\dataset\ImageSets\Main\\2007_val.txt'
convert_annotation_format(input_file, output_file)
