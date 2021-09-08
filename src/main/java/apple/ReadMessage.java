package apple;

public class ReadMessage {
	/*
	 *
	 * ©°©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©Ð©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©´ ©¦ len_1 ©¦ message_1 ©¦
	 * ©¸©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©Ø©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¼ <- 4 bytes -> <------- len_1
	 * bytes ------>
	 *
	 * ©°©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©Ð©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©Ð©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤
	 * ©¤©¤©¤©¤©¤©¤©Ð©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤ ©¦ chunk_1 ©¦ chunk_2 ©¦ chunk_3 ©¦ chunk_4 ...
	 * ©À©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©Ð©¤©¤©¤©Ø©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©Ø©¤©¤©Ð©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©Ð©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤
	 * ©¤©¤©¤©¤©¤©¤©Ø©¤©¤©Ð©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©Ð©¤©¤©¤ ©¦ len_1 ©¦ message_1 ©¦ len_2 ©¦ message_2 ©¦
	 * len_3 ©¦...
	 * ©¸©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©Ø©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©Ø©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©Ø©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤
	 * ©¤©¤©¤©¤©¤©¤©¤©¤©¤©Ø©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©Ø©¤©¤©¤ <- 4 bytes -> <---- len_1 bytes ----> <- 4
	 * bytes -> <------- len_2 bytes -------> <- 4 bytes ->
	 *
	 *
	 * accept(chunk_1) accept(chunk_2) accept(chunk_3) -> triggers a call to
	 * process(message_1) accept(chunk_4) -> triggers a call to
	 * process(message_2) accept(chunk_5) ...
	 *
	 *
	 * // len = 4; // length_byte [a b c d] // message_len = abcd; // message [e
	 * f g h ] // length = 5; // index = 0; // message_ index = 1;
	 *
	 *
	 *
	 */

	class StreamOfMessages {

		// define state here, if needed:
		// (TODO)
		int len = 0;
		byte[] length_byte = new byte[4];

		int message_len = 0;
		int message_index = 0;
		byte[] message;

		// called when a chunk is received from the network.
		public void accept(byte[] chunk) {
			int length = chunk.length;
			int index = 0;
			while (index < length) {
				if (len < 4) {
					length_byte[len++] = chunk[index++];
					if (len == 4) {
						message_len = parse(length_byte);
						message = new byte[message_len];
					}
				} else {
					message[message_index++] = chunk[index++];
					if (message_index == message_len) {
						process(message);
						len = 0;
						message_index = 0;
					}

				}

			}

		}

		private int parse(byte[] length_byte) {
			return 0;
		}

		private void process(byte[] message) {
		}; // call this method with the
			// reconstituted message.

	}
}
