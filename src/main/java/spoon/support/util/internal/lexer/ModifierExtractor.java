package spoon.support.util.internal.lexer;

import spoon.reflect.cu.SourcePosition;
import spoon.reflect.declaration.ModifierKind;
import spoon.support.reflect.CtExtendedModifier;
import spoon.support.util.internal.trie.WordTrie;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ModifierExtractor {
	private final WordTrie<ModifierKind> modifierTrie = WordTrie.ofWords(
			Arrays.stream(ModifierKind.values())
					.collect(Collectors.toMap(ModifierKind::toString, Function.identity()))
	);

	public void collectModifiers(
			char[] content,
			int start,
			int end,
			Map<ModifierKind, CtExtendedModifier> modifiers,
			BiFunction<Integer, Integer, SourcePosition> createSourcePosition
	) {
		JavaLexer lexer = new JavaLexer(content, start, end);
		while (!modifiers.isEmpty()) {
			Token lex = lexer.lex();
			if (lex == null) {
				return;
			}
			lex = fixupNonSealed(lex, lexer, content);
			Optional<ModifierKind> match = modifierTrie.findMatch(content, lex.start(), lex.end());
			if (match.isPresent()) {
				CtExtendedModifier modifier = modifiers.remove(match.get());
				if (modifier != null) {
					modifier.setPosition(createSourcePosition.apply(lex.start(), lex.end() - 1));
				}
			}
		}
	}

	private Token fixupNonSealed(Token start, JavaLexer lexer, char[] content) {
		int nextStart = start.end();
		if (isNon(start, content) && nextStart + "sealed".length() < content.length) {
			if (isDashSealed(content, nextStart)) {
				lexer.lex(); // skip -
				lexer.lex(); // skip sealed
				return new Token(TokenType.KEYWORD, start.start(), start.end() + "-sealed".length());
			}
		}
		return start;
	}

	private static boolean isNon(Token token, char[] content) {
		int pos = token.start();
		return token.length() == 3 && content[pos++] == 'n' && content[pos++] == 'o' && content[pos] == 'n';
	}

	private static boolean isDashSealed(char[] content, int pos) {
		return content[pos++] == '-' &&
				content[pos++] == 's' &&
				content[pos++] == 'e' &&
				content[pos++] == 'a' &&
				content[pos++] == 'l' &&
				content[pos++] == 'e' &&
				content[pos] == 'd';
	}
}
