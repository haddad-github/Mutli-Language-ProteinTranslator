#Read file and return list
def read_file(file):

    #Open file as read only and read every line while removing '\n'
    with open(file, 'r') as f:
        rows = f.read().splitlines()

    #all_rows as a list of list
    all_rows = []
    #Loop through rows (starting from 2nd, to ignore header)
    for row in rows[1:]:
        #Split row based on comma
        all_rows.append(row.split(','))

    return all_rows

#Create dictionary
def create_dict(rows):

    #Store 1st item of the row as the key (codon)
    #Store 3rd item of the row as the value (amino acid)
    amino_codon_dict = {}
    for row in rows:
        codon = row[0]
        amino_acid_letter = row[2]

        #Transform "Stop" into "_"
        if amino_acid_letter == "Stop":
            amino_acid_letter = "_"

        amino_codon_dict[codon] = amino_acid_letter

    return amino_codon_dict

def turnUppercase(seq):
    return seq.upper()

#Check if user entered a valid RNA sequence (no letters other than A,U,G,C)
def validate_sequence(seq):

    accepted = ['A', 'U', 'C', 'G']

    for letter in seq:
        if letter not in accepted:
            raise Exception("Sequence contains an faulty letter (one that is not A, U, C, G)")

#Splits string into list of substrings separated by n characters
def split_into_nth(a_string, nth):

    #Store the substring
    the_list = []
    for i in range(0, len(a_string), nth):
        substring = a_string[i:i+nth]
        the_list.append(substring)
    return the_list

#Remove codons that are incomplete (less than 3 nucleotides)
def remove_incomplete_codons(list_of_codons):
    for codon in list_of_codons:
        if len(codon) < 3:
            list_of_codons.remove(codon)

    return list_of_codons

#Translate given sequence with previously created dictionary
def translate_RNA_to_Protein(list_of_codons):

    protein = ""
    for codon in list_of_codons:
        try:
            protein += amino_dict[codon]
        except KeyError:
            print("Codon not found in the table")
    return protein

#Read file and rows
file = r'C:\Users\rorop\Desktop\Script_In_All_Languages\codon_table.csv'
file_read = read_file(file)

#Create dictionary
amino_dict = create_dict(file_read)

#Ask user for RNA and turn to uppercase
user_sequence = input("Enter your DNA sequence: ")
user_sequence_upper = turnUppercase(user_sequence)

#Validate sequence
validate_sequence(user_sequence_upper)

#Split sequence into codons and remove codons that aren't 3 letters
seq_split = split_into_nth(user_sequence_upper, 3)
seq_complete = remove_incomplete_codons(seq_split)

#Translate sequence into protein
print(translate_RNA_to_Protein(seq_complete))

