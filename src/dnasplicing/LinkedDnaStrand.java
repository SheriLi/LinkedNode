package dnasplicing;

public class LinkedDnaStrand implements DnaStrand {
	private DnaSequenceNode firstNode;
	private DnaSequenceNode lastNode;
	int nodeCount;
	int appendCount = 0;


	public LinkedDnaStrand(String dnaSequence) {
		DnaSequenceNode newNode = new DnaSequenceNode(dnaSequence);
		firstNode = newNode;
		lastNode = newNode;
		nodeCount++;
	}


	@Override
	public void append(String dnaToAppend) {
		DnaSequenceNode appendNode = new DnaSequenceNode(dnaToAppend);
		lastNode.next = appendNode;
		appendNode.previous = lastNode;
		lastNode = appendNode;
		lastNode.next = null;
		if (lastNode.dnaSequence.length() != 0) {
			appendCount++;
		}
		nodeCount++;
	}


	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		DnaSequenceNode cursor = firstNode;
		while (cursor != null) {
			sb.append(cursor.dnaSequence);
			cursor = cursor.next;
		}
		return sb.toString();
	}


	@Override
	public long getNucleotideCount() {
		StringBuilder sb = new StringBuilder();
		DnaSequenceNode cursor = firstNode;
		while (cursor != null) {
			sb.append(cursor.dnaSequence);
			cursor = cursor.next;
		}
		return sb.length();
	}


	@Override
	public DnaStrand cutSplice(String enzyme, String splicee) {
		int pos = 0;
		int start = 0;
		String find = toString();
		boolean b = true;
		LinkedDnaStrand lds = null;
		while ((pos = find.indexOf(enzyme, pos)) >= 0) {
			if (b) {
				if (pos == 0) {
					lds = new LinkedDnaStrand(splicee);
				} else {
					lds = new LinkedDnaStrand(find.substring(start, pos));
				}
				b = false;
			} else {
				lds.append(find.substring(start, pos));
			}

			if (pos != 0) {
				lds.append(splicee);
			}
			start = pos + enzyme.length();
			pos++;
		}

		if (start < find.length()) {
			if (lds == null) {
				lds = new LinkedDnaStrand("");
			} else {
				lds.append(find.substring(start));
			}
		}
		return lds;
	}


	@Override
	public DnaStrand createReversedDnaStrand() {
		StringBuilder copy = new StringBuilder(toString());
		LinkedDnaStrand l = new LinkedDnaStrand(copy.reverse().toString());
		return l;
	}


	@Override
	public int getAppendCount() {
		return appendCount;
	}


	@Override
	public DnaSequenceNode getFirstNode() {
		return firstNode;
	}


	@Override
	public int getNodeCount() {
		return nodeCount;
	}

}
