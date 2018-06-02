package edu.tseidler;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Main {
	public static void main(String[] args) throws IOException, URISyntaxException {
		Path path = Paths.get((Main.class.getResource("book.txt").toURI()));
		System.out.println(path.toString());
		Long t1 = Instant.now().toEpochMilli();
		Map<String, Long> words = Files.lines(path, Charset.forName("Cp1252"))
				// .parallel() //
				.map(l -> l.split("\\s+")) //
				.flatMap(Arrays::stream) //
				.filter(w -> !w.isEmpty()) //
				.map(w -> w.replaceAll("\\W", ""))
				.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

		Long t2 = Instant.now().toEpochMilli();
		System.out.println(words.size());
		words.entrySet().stream() //
				.sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())) //
				.limit(50) //
				.forEach(e -> System.out.printf("%s: %s\n", e.getKey(), e.getValue()));

		Long t3 = Instant.now().toEpochMilli();

		System.out.printf("total time: %d\n", t3 - t1);
		System.out.printf("reading time: %d\n", t2 - t1);
		System.out.printf("sorting time: %d\n", t3 - t2);
	}
}
